#!/usr/bin/env python3
import json, requests, sqlite3, hug, os, re, time, urllib.request, shutil, zipfile
try:
    from bs4 import BeautifulSoup
except ImportError:
    os.system("pip3 install beautifulsoup4")

#http://localhost:8000/scan?ip=192.168.43.1&port=53&rate=5&parstype=1
#vulners parstype=1
#localdb parstype=2

res = ''

def get_ip(ip, port, rate):

    if os.path.exists("mres.txt"):
        os.remove("mres.txt")
    if os.path.exists("nres.xml"):
       os.remove("nres.xml")

    print('masscan '+ip+' -Pn --ports '+port+' --show open,closed '+rate+' -oG mres.txt')

    os.system('masscan '+ip+' -Pn --ports '+port+' --show open,closed '+rate+' -oG mres.txt')
    hosts = {}
    with open('mres.txt','r') as f:
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
        os.system("%s -p %s --script vulners --script-args mincvss=5.0 --append-output -oX nres.xml" % ("nmap -sV -Pn "+host, ",".join(hosts[host])))

def get_html(url):
    r = requests.get(url)
    b = BeautifulSoup(r.text, 'lxml')
    return b

def get_json(url):
    b = get_html(url)
    json = b.find('div',id='jsonbody').text.strip()
    return json

def get_host(json):
    url = 'localhost'
    r = requests.post(url, data=json)


def vulners_parser(list_port, adress):
    global res

    CVE_JSON = '{'
    for list_p in list_port:
        port1 = list_p.split('|')[0]
        port = port1.split(':')[0].strip('"')
        state = port1.split(',')[0].split(':')[-1].strip('"')
        reason = port1.split(',')[1].split(':')[-1].strip('"')
        name = port1.split(',')[2].split(':')[-1].strip('"')
        product = port1.split(',')[3].split(':')[-1].replace('}', '').strip('"')

        try:
            list_cve = list_p.split('|')[1].split(',')
        except:
            list_cve = []

        if list_cve == []:
            CVE_JSON += json.dumps({port: {"state": state, "reason": reason, "name": name, "product": product}}).strip(
                '{').replace('}}', '}') + ','
        else:
            list_CVE_ALL = ''

            for cve in list_cve:
                # print(cve)

                url_cve = 'https://vulners.com/cve/' + cve
                info = get_json(url_cve)
                # print(info)

                list_CVE_ALL += info.replace('\\"', '') + ','
            list_CVE_ALL = list_CVE_ALL.strip(',')
            # print(list_CVE_ALL)

            CVE_JSON += json.dumps({port: {"state": state, "reason": reason, "name": name, "product": product,
                                           "CVE": [list_CVE_ALL]}}).strip('{').replace(']}}', ']}').replace('"{','{')\
                            .replace('}"', '}') + ','

    CVE_JSON = CVE_JSON.replace('\\', '').strip(',') + '}'
    # print(CVE_JSON)
    tmp = '"' + adress + '":' + CVE_JSON
    res += tmp + ','


def db_script(list_port, adress):
    global res

    if not os.path.exists("db"):
       os.makedirs("db")

    conn = sqlite3.connect("db/cve.db")
    cursor = conn.cursor()

    CVE_JSON = '{'
    for list_p in list_port:
        #print(list_p)
        port1 = list_p.split('|')[0]
        #print(port1)
        port = port1.split(':')[0].strip('"')
        state = port1.split(',')[0].split(':')[-1].strip('"')
        reason = port1.split(',')[1].split(':')[-1].strip('"')
        name = port1.split(',')[2].split(':')[-1].strip('"')
        product = port1.split(',')[3].split(':')[-1].replace('}', '').strip('"')

        try:
            list_cve = list_p.split('|')[1].split(',')
        except:
            list_cve = []

        if list_cve == []:
            CVE_JSON += json.dumps({port: {"state": state, "reason": reason, "name": name, "product": product}})[1:-1] + ','
        else:
            list_CVE_ALL = ''

            for cve in list_cve:

                table_name = 'cve' + cve.split('-')[1]

                cursor.execute("SELECT * FROM " + table_name + " WHERE cve = '" + cve + "';")
                r = cursor.fetchall()
                results = str(r).replace("[('","").replace("')]","").split("', '")
                #print(r)


                try:
                    products = results[1].split(';')
                    list_prod = []
                    for product in products:
                        list_prod.append(product)

                except:
                    list_prod = []

                #try:
                published_datetime = results[2]
                #except:
                    #published_datetime = ''

                try:
                    last_modified_datetime = results[3]
                except:
                    last_modified_datetime = ''

                try:
                    score = results[4]
                except:
                    score = ''

                try:
                    access_vector = results[5]
                except:
                    access_vector = ''

                try:
                    access_complexity = results[6]
                except:
                    access_complexity = ''

                try:
                    authentication = results[7]
                except:
                    authentication = ''

                try:
                    confidentiality_impact = results[8]
                except:
                    confidentiality_impact = ''

                try:
                    integrity_impact = results[9]
                except:
                    integrity_impact = ''

                try:
                    availability_impact = results[10]
                except:
                    availability_impact = ''

                try:
                    generated_on_datetime = results[11]
                except:
                    generated_on_datetime = ''

                try:
                    cwe_id = results[12].split(';')
                    list_cwe_id = []
                    for id in cwe_id:
                        list_cwe_id.append(id)
                except:
                    list_cwe_id = []

                try:
                    referenc = results[13].split(';')
                    list_referenc = []
                    for ref in referenc:
                        list_referenc.append(ref)
                except:
                    list_referenc = []

                try:
                    summary = results[14].replace('\\"','')
                except:
                    summary = ''

                json_cve = json.dumps({cve:{'products':list_prod,'published_datetime':published_datetime,
                                             'last_modified_datetime':last_modified_datetime,'score':score,
                                             'access_vector':access_vector,'access_complexity':access_complexity,
                                             'authentication':authentication,'confidentiality_impact':confidentiality_impact,
                                             'integrity_impact':integrity_impact,'availability_impact':availability_impact,
                                             'generated_on_datetime':generated_on_datetime,'cwe_id':list_cwe_id,
                                             'referenc':list_referenc,'summary':summary}}).replace('\\"','')#.replace('{"C','"C').replace('}}','}')
                #print(json_cve)
                list_CVE_ALL += json_cve + ','
            #print(list_CVE_ALL.strip(','))
            list_CVE_ALL = '[' + list_CVE_ALL.strip(',') + ']'
            CVE_JSON += json.dumps({port: {"state": state, "reason": reason, "name": name, "product": product,
                                           "CVE": list_CVE_ALL}}).replace('"[', '[').replace(']"', ']')[1:-1] + ','
    CVE_JSON = CVE_JSON.replace('\\"','"').strip(',') + '}'

    tmp = '"' + adress + '":' + CVE_JSON
    res += tmp + ','

def get_html(url):
    r = requests.get(url)
    b = BeautifulSoup(r.text, 'lxml')
    return b


def get_urls(url):
    b = get_html(url)
    row = b.find('div',class_='container',id='body-section').find_all('div',class_='row')[1].find('tbody').find_all('tr')
    #print(row)
    list_url = []
    for r in row:
        data_testid = r.get('data-testid')
        if 'zip' in data_testid:
            a = r.find('a').get('href')
            if a.split('.')[-1] == 'gz':
                continue
            else:
                list_url.append(a)

    return list_url

def get_zip(urls):
    if not os.path.exists("db"):
        os.makedirs("db")

    for url in urls[2:]:
        print(url.split('/')[-1])

        output_file = "db/" + url.split('/')[-1]
        with urllib.request.urlopen(url) as response, open(output_file, 'wb') as out_file:
            shutil.copyfileobj(response, out_file)

        with zipfile.ZipFile(output_file, 'r') as zip_ref:
            zip_ref.extractall('db')

        table_name = 'cve' + output_file.replace('.xml.zip','').split('-')[-1]
        table = 'cve text NOT NULL,' \
                'products text,' \
                'published_datetime text NOT NULL,' \
                'last_modified_datetime text NOT NULL,' \
                'score text,' \
                'access_vector text,' \
                'access_complexity text,' \
                'authentication text,' \
                'confidentiality_impact text,' \
                'integrity_impact text,' \
                'availability_impact text,' \
                'generated_on_datetime text,' \
                'cwe_id text,' \
                'referenc text,' \
                'summary text'
        conn = sqlite3.connect("db/cve.db")
        cursor = conn.cursor()
        print(table_name)
        cursor.execute("CREATE TABLE IF NOT EXISTS " + table_name + "(" + table + ")")
        conn.commit()
        conn.close()

        print(output_file)
        xml_file = open(output_file.replace(".zip","")).read()
        #print(xml_file)
        get_info(xml_file,table_name)



def get_info(xml_file,table_name):

    conn = sqlite3.connect('db/cve.db')
    cursor = conn.cursor()
    xml_1 = xml_file.split('<entry')[1:]
    #print(len(xml_1))
    n = 0
    for x in xml_1[:]:
        xml_2 = '<entry' + x
        b = BeautifulSoup(xml_2,'lxml')
        #print(b)

        try:
            CVE = b.find('vuln:cve-id').text.replace("'",'')
        except:
            CVE = 'None'
            continue
        #print('CVE : ' + CVE)
        try:
            vulnerable_software_list = b.find('vuln:vulnerable-software-list').find_all('vuln:product')
            prod = ''
            for product in vulnerable_software_list:
                prod += product.text.strip() + '; '
            prod = prod.replace("'",'').strip('; ')
        except:
            prod = 'None'
        #print('prod : ' + prod)

        try:
            published_datetime = b.find('vuln:published-datetime').text.replace("'",'').strip()
        except:
            published_datetime = 'None'
        #print('published_datetime : ' + published_datetime)

        try:
            last_modified_datetime = b.find('vuln:last-modified-datetime').text.replace("'",'').strip()
        except:
            last_modified_datetime = 'None'
        #print('last_modified_datetime : ' +  last_modified_datetime)

        try:
            score = b.find('cvss:score').text.replace("'",'').strip()
        except:
            score = 'None'
        #print('score : ' + score)

        try:
            access_vector = b.find('cvss:access-vector').text.replace("'",'').strip()
        except:
            access_vector = 'None'
        #print('access_vector : ' + access_vector)
        try:
            access_complexity = b.find('cvss:access-complexity').text.replace("'",'').strip()
        except:
            access_complexity = 'None'
        #print('access_complexity : ' + access_complexity)

        try:
            authentication = b.find('cvss:authentication').text.replace("'",'').strip()
        except:
            authentication = 'None'
        #print('authentication : ' + authentication)

        try:
            confidentiality_impact = b.find('cvss:confidentiality-impact').text.replace("'",'').strip()
        except:
            confidentiality_impact = 'None'
        #print('confidentiality_impact : ' + confidentiality_impact)

        try:
            integrity_impact = b.find('cvss:integrity-impact').text.replace("'",'').strip()
        except:
            integrity_impact = 'None'
        #print('integrity_impact : ' + integrity_impact)

        try:
            availability_impact = b.find('cvss:availability-impact').text.replace("'",'').strip()
        except:
            availability_impact = 'None'
        #print('availability_impact : ' + availability_impact)

        try:
            generated_on_datetime = b.find('cvss:generated-on-datetime').text.replace("'",'').strip()
        except:
            generated_on_datetime = 'None'
        #print('generated_on_datetime : ' + generated_on_datetime)

        try:
            cwe = b.find_all('vuln:cwe')
            cwe_id = ''
            for id in cwe:
                ID = id.get('id')
                cwe_id += ID + '; '
            cwe_id = cwe_id.replace("'",'').strip('; ')
            if cwe_id == '':
                cwe_id = 'None'
        except:
            cwe_id = 'None'
        #print('cwe_id : ' + cwe_id)

        try:
            references = b.find_all('vuln:references')
            referenc = ''
            for ref in references:
                source = ref.find('vuln:source').text.strip()
                reference = ref.find('vuln:reference').get('href')
                referenc += source + ' ' + reference + '; '
            referenc = referenc.replace("'",'').strip('; ')
        except:
            referenc = 'None'
        #print('referenc : ' + referenc)

        try:
            summary = b.find('vuln:summary').text.replace("'",'').strip()
        except:
            summary = 'None'
        #print('summary : ' + summary)

        v = "'" + CVE + "','" + prod + "','" + published_datetime + "','" + last_modified_datetime + "','" + \
            score + "','" + access_vector + "','" + access_complexity + "','" + authentication + "','" + \
            confidentiality_impact + "','" + integrity_impact + "','" + availability_impact + "','" + \
            generated_on_datetime + "','" + cwe_id + "','" + referenc + "','" + summary + "'"
        print(v)

        cursor.execute("INSERT INTO " + table_name + " VALUES  (" + v + ")")

        cursor.execute("SELECT * FROM " + table_name)
        resultsC = cursor.fetchall()
        #print(resultsC)
        n +=1
        print(n)
    conn.commit()
    conn.close()


def get_CVE(parstype):
    xml = open('nres.xml').read()
    # print(xml)

    b = BeautifulSoup(xml, 'lxml')
    # print(b)

    host = b.find_all('host')
    for h in host:
        # print(h)
        adress = h.find('address').get('addr')
        #print(adress)


        port = h.find_all('port')
        #print(port)

        list_portInfo = []
        for p in port:
            portid = p.get('portid')
            state = p.state.get('state')
            #print(state)
            reason = p.state.get('reason')
            #print(reason)


            name = p.service.get('name')
            if name == None:
                name = ''

            product = p.service.get('product')
            if product == None:
                product = ''

            portInfo = '"' + portid + '":{"state":"' + state + '","reason":"' + reason + '","name":"' + name + '","product":"' + product + '"}'

            vulners_teg = p.find('script', id='vulners')
            if vulners_teg == None:
                list_portInfo.append(portInfo)
            else:
                # print(port)
                vulners = vulners_teg.get('output').split()
                list_CVE = ''
                for p in vulners:
                    if 'https' in p:
                        url = p.split('/')[-1]
                        list_CVE += url + ','
                list_portInfo.append(portInfo + '|' + list_CVE.strip(','))
        if parstype == 1:
            vulners_parser(list_portInfo, adress)
        if parstype == 2:
            db_script(list_portInfo, adress)


@hug.get('/scan',output=hug.output_format.json)
def scan(ip: hug.types.text, port: hug.types.text, rate: hug.types.text, parstype: int):

    get_ip(ip, port, "--rate=" + rate)
    if os.path.exists("nres.xml"):
        get_CVE(parstype)
        result = "{" + str(res).strip(",").replace("\\","") + "}"
        print(result)
        return json.loads(result)

@hug.get('/update')
def update():
    b = time.localtime()
    print(time.asctime(b))

    urls = get_urls('https://nvd.nist.gov/vuln/data-feeds#JSON_FEED')
    get_zip(urls)

    e = time.localtime()
    print(time.asctime(e))

if __name__ == '__main__':
    main()