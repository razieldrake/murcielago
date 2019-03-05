#!/usr/bin/env python3
import json, requests, sqlite3, hug, os, re, time, urllib.request, shutil, zipfile

try:
    from bs4 import BeautifulSoup
except ImportError:
    os.system("pip3 install beautifulsoup4")

# http://localhost:8000/scan?ip=83.239.139.50%2089.29.121.14&port=80%2c443&rate=3&parstype=2
# http://localhost::8000/scan?ip=41.39.188.235&port=135%2c139%2c443%2c445%2c8083&rate=5&parstype=2
# vulners parstype=1
# localdb parstype=2

def get_ip(ip, port, rate):
    if os.path.exists("mres.txt"):
        os.remove("mres.txt")
    if os.path.exists("nres.xml"):
        os.remove("nres.xml")

    print('masscan ' + ip + ' -Pn --ports ' + port + ' --show open ' + rate + ' -oG mres.txt')

    os.system('masscan ' + ip + ' -Pn --ports ' + port + ' ' + rate + ' -oG mres.txt')
    hosts = {}
    with open('mres.txt', 'r') as f:
        for line in f.readlines():
            line = (line.split("#")[0]).strip()
            if line == "":
                continue
            m = re.search("Host: (\d{1,3}.\d{1,3}.\d{1,3}.\d{1,3}).*Ports: (\d{1,})", line)
            if m is None:
                continue
            if m.group(1) in hosts.keys():
                hosts[m.group(1)].append(m.group(2))
            else:
                hosts[m.group(1)] = [m.group(2)]
    for host in hosts:
        os.system("%s -p %s --script vulners --script-args mincvss=5.0 --append-output -oX nres.xml" % ("nmap -Pn -sV  -O --osscan-limit " + host, ",".join(hosts[host])))
        #print("debug: " + "%s -p %s --script vulners --script-args mincvss=5.0 --append-output -oX nres.xml" % ("nmap -Pn -sV -O --osscan-limit " + host, ",".join(hosts[host])))


def get_html(url):
    r = requests.get(url)
    b = BeautifulSoup(r.text, 'lxml')
    return b


def get_json(url):
    b = get_html(url)
    json = b.find('div', id='jsonbody').text.strip()
    return json


def get_host(json):
    url = 'localhost'
    r = requests.post(url, data=json)


def vulners_parser(list_port, adress):
    global res

    CVE_ADRESS = json.dumps({"IP":adress,"ports": []})
    cve_adress = json.loads(CVE_ADRESS)

    CVE_JSON = ''
    for list_p in list_port:
        port1 = list_p.split('|')[0].replace('"','')
        port = port1.split(':')[0].strip('"')
        state = port1.split(',')[0].split(':')[-1]
        reason = port1.split(',')[1].split(':')[-1]
        name = port1.split(',')[2].split(':')[-1]
        product = port1.split(',')[3].split(':')[-1].replace('}', '')

        try:
            list_cve = list_p.split('|')[1].split(',')
        except:
            list_cve = []

        if list_cve == []:
            CVE_PORT = json.dumps({"portnumber": port,"state": state, "reason": reason, "name": name, "product": product})
            cve_port = json.loads(CVE_PORT)
        else:
            CVE_PORT = json.dumps({"portnumber": port, "state": state, "reason": reason, "name": name, "product": product, "CVE": []})
            cve_port = json.loads(CVE_PORT)

            for cve in list_cve:

                url_cve = 'https://vulners.com/cve/' + cve
                info = get_json(url_cve)
                info_json = json.loads(info)

                cve_port['CVE'].append(info_json)


        cve_adress['ports'].append(cve_port)

    return cve_adress


def db_script(list_port, adress, operation_system):
    global res

    CVE_ADRESS = json.dumps({"ip": adress, "operation_system": operation_system, "ports": []})
    cve_adress = json.loads(CVE_ADRESS)

    if not os.path.exists("db"):
        os.makedirs("db")

    conn = sqlite3.connect("db/cve.db")
    cursor = conn.cursor()

    CVE_JSON = '{'
    for list_p in list_port:
        port1 = list_p.split('|')[0].replace('"', '')
        port = port1.split(':')[0].strip('"')
        state = port1.split(',')[0].split(':')[-1]
        reason = port1.split(',')[1].split(':')[-1]
        name = port1.split(',')[2].split(':')[-1]

        name = port1.split(',')[2].split(':')[-1]

        product = port1.split(',')[3].split(':')[-1]

        version= port1.split(',')[4].split(':')[-1].replace('}', '')
        #print("ver:   " + version)

        try:
            list_cve = list_p.split('|')[1].split(',')
        except:
            list_cve = []

        service_js = json.dumps({"product": product, "version": version})
        service_ds = json.loads(service_js)

        if list_cve == []:
            CVE_PORT = json.dumps({"portnumber": port, "state": state, "reason": reason, "name": name, "service": [{"product": product, "version": version}]})
            cve_port = json.loads(CVE_PORT)

            #cve_port['service'].append(cve_port)
        else:
            CVE_PORT = json.dumps({"portnumber": port, "state": state, "reason": reason, "name": name, "service": [{"product": product, "version": version, "CVE": []}]})
            cve_port = json.loads(CVE_PORT)

            #cve_port['service'].append(service_ds)


            for cve in list_cve:

                table_name = 'cve' + cve.split('-')[1]

                cursor.execute("SELECT * FROM " + table_name + " WHERE cve = '" + cve + "';")
                r = cursor.fetchall()
                results = r[0]
                cve = str(results[0])

                try:
                    baseScore = results[1]
                    if baseScore == '':
                        baseScore = results[3]
                except:
                    baseScore = ''

                try:
                    impactScore = results[2]
                    if impactScore == '':
                        impactScore = results[4]
                except:
                    impactScore = ''

                try:
                    vectorString = results[5]
                    if vectorString == '':
                        vectorString = results[7]
                except:
                    vectorString = ''

                try:
                    attackVector = results[6]
                    if attackVector == '':
                        attackVector = results[8]
                except:
                    attackVector = ''

                try:
                    publishedDate = results[9]
                except:
                    publishedDate = ''

                try:
                    lastModifiedDate = results[10]
                except:
                    lastModifiedDate = ''

                try:
                    description = results[11]
                except:
                    description = ''



                JSON_CVE = json.dumps({'cveID': cve,'baseScore': baseScore, 'impactScore': impactScore,
                                       'vectorString': vectorString, 'attackVector': attackVector,
                                       'publishedDate': publishedDate,'lastModifiedDate': lastModifiedDate,
                                       'description': description})
                json_cve = json.loads(JSON_CVE)
                cve_port['service'][0]['CVE'].append(json_cve)

        cve_adress['ports'].append(cve_port)

    return cve_adress


def get_html(url):
    r = requests.get(url)
    b = BeautifulSoup(r.text, 'lxml')
    return b


def get_urls(url):
    b = get_html(url)
    row = b.find('div', class_='container', id='body-section').find_all('div', class_='row')[0].find('tbody').find_all('tr')
 
    list_url = []
    for r in row:
        a = r.find_all('a')
        for zip in a:
            if 'zip' in zip.get('href'):
                href = zip.get('href')
                list_url.append(href)

    return list_url

def get_zip(urls):
    if not os.path.exists("db"):
        os.makedirs("db")

    for url in urls[2:]:
        print(url.split('/')[-1])
        output_file = "db/" + url.split('/')[-1]

        print(output_file)
        with urllib.request.urlopen(url) as response, open(output_file, 'wb') as out_file:
            shutil.copyfileobj(response, out_file)

        print(output_file)
        with zipfile.ZipFile(output_file, 'r') as zip_ref:
            zip_ref.extractall('db')

        db_name = 'db/cve.db'
        table_name = 'cve' + output_file.replace('.json.zip', '').split('-')[-1]
        table = 'cve text NOT NULL,' \
                'baseScoreV3 float,' \
                'impactScoreV3 float,' \
                'baseScoreV2 float,' \
                'impactScoreV2 float,' \
                'vectorStringV3 text,' \
                'attackVectorV3 text,' \
                'vectorStringV2 text,' \
                'accessVectorV2 text,' \
                'publishedDate text,' \
                'lastModifiedDate text,' \
                'description text'
        conn = sqlite3.connect('db/cve.db')
        cursor = conn.cursor()
        print(table_name)
        cursor.execute("CREATE TABLE IF NOT EXISTS " + table_name + "(" + table + ")")
        conn.commit()
        conn.close()

        json_file = open(output_file.replace('.zip', ''))
        get_info(json_file, table_name)


def get_info(json_file, table_name):
    conn = sqlite3.connect('db/cve.db')
    cursor = conn.cursor()
    data = json.load(json_file)
    CVE_Items = data["CVE_Items"]
    n = 0
    for CVE in CVE_Items:
        impact = CVE['impact'].keys()

        try:
            baseScoreV3 = CVE['impact']['baseMetricV3']['cvssV3']['baseScore']
        except:
            baseScoreV3 = ''

        try:
            impactScoreV3 = CVE['impact']['baseMetricV3']['impactScore']
        except:
            impactScoreV3 = ''

        try:
            baseScoreV2 = CVE['impact']['baseMetricV2']['cvssV2']['baseScore']
        except:
            baseScoreV2 = ''

        try:
            impactScoreV2 = CVE['impact']['baseMetricV2']['impactScore']
        except:
            impactScoreV2 = ''

        try:
            vectorStringV3 = CVE['impact']['baseMetricV3']['cvssV3']['vectorString']
        except:
            vectorStringV3 = ''

        try:
            attackVectorV3 = CVE['impact']['baseMetricV3']['cvssV3']['attackVector']
        except:
            attackVectorV3 = ''

        try:
            vectorStringV2 = CVE['impact']['baseMetricV2']['cvssV2']['vectorString']
        except:
            vectorStringV2 = ''

        try:
            accessVectorV2 = CVE['impact']['baseMetricV2']['cvssV2']['accessVector']
        except:
            accessVectorV2 = ''

        cveID = CVE['cve']['CVE_data_meta']['ID']

        publishedDate = CVE['publishedDate']

        lastModifiedDate = CVE['lastModifiedDate']

        description = CVE['cve']['description']['description_data'][0]['value'].replace("'", '').strip()
        try:
            isc = CVE['impact']['baseMetricV3']

            v = "'" + cveID + "','" + str(baseScoreV3) + "','" + str(impactScoreV3) + "','" + '' + "','" + \
                '' + "','" + str(vectorStringV3) + "','" + str(attackVectorV3) + "','" + '' + "','" + \
                '' + "','" + publishedDate + "','" + lastModifiedDate + "','" + \
                description + "'"

            cursor.execute("INSERT INTO " + table_name + " VALUES  (" + v + ")")

            cursor.execute("SELECT * FROM " + table_name)
            resultsC = cursor.fetchall()

        except:

            v = "'" + cveID + "','" + '' + "','" + '' + "','" + str(baseScoreV2) + "','" + \
                str(impactScoreV2) + "','" + '' + "','" + '' + "','" + str(vectorStringV2) + "','" + \
                str(accessVectorV2) + "','" + publishedDate + "','" + lastModifiedDate + "','" + \
                description + "'"

            cursor.execute("INSERT INTO " + table_name + " VALUES  (" + v + ")")

            # cursor.execute("SELECT * FROM " + table_name)
            # resultsC = cursor.fetchall()
            # print(resultsC)

        n += 1
        print
    conn.commit()
    conn.close()


def get_CVE(parstype):
    xml = open('nres.xml').read()

    b = BeautifulSoup(xml, 'lxml')

    host = b.find_all('host')
    CVE_HOST = json.dumps({"host":[]})
    cve_host = json.loads(CVE_HOST)
    for h in host:
        adress = h.find('address').get('addr')

        port = h.find_all('port')
        
        try:
            operation_system = h.find('os').get('name')
        except:
            operation_system = "None"

        list_portInfo = []
        for p in port:
            portid = p.get('portid')
            state = p.state.get('state')
            reason = p.state.get('reason')

            name = p.service.get('name')
            if name == None:
                name = ''

            product = p.service.get('product')
            if product == None:
                product = ''

            version = p.service.get('version')
            if version == None:
                version = ''

            #portInfo = '"' + portid + '":{"state":"' + state + '","reason":"' + reason + '","name":"' + name + '","product":"' + product + '"}'
            portInfo = '"' + portid + '":{"state":"' + state + '","reason":"' + reason + '","name":"' + name + '","product":"' + product + '","version":"' + version +'"}'
            #print(portInfo)

            vulners_teg = p.find('script', id='vulners')
            if vulners_teg == None:
                list_portInfo.append(portInfo)
            else:
                vulners = vulners_teg.get('output').split()
                list_CVE = ''
                for p in vulners:
                    if 'https' in p:
                        url = p.split('/')[-1]
                        list_CVE += url + ','
                list_portInfo.append(portInfo + '|' + list_CVE.strip(','))
        if parstype == 1:
            json_adress = vulners_parser(list_portInfo, adress)
            cve_host['host'].append(json_adress)
        if parstype == 2:
            json_adress = db_script(list_portInfo, adress, operation_system)
            cve_host['host'].append(json_adress)

    return cve_host


@hug.get('/scan', output=hug.output_format.json)
def scan(ip: hug.types.text, port: hug.types.text, rate: hug.types.text, parstype: int):

    get_ip(ip, port, "--rate=" + rate)
    if os.path.exists("nres.xml"):
        result = get_CVE(parstype)
        print(result)
        return result


@hug.get('/update')
def update():
    b = time.localtime()
    print(time.asctime(b))

    urls = get_urls('https://nvd.nist.gov/vuln/data-feeds#JSON_FEED')
    get_zip(urls)

    e = time.localtime()
    print(time.asctime(e))