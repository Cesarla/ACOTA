<img src="http://weso.es/img/logo_acota_850.png">
# ACOTA:  Automatic Collaborative Tagging

```
  Copyright 2012 WESO Research Group

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```

## What is it? ##
ACOTA (Automatic Collaborative Tagging). It is a Java-based library for suggesting 
tags in a collaborative and automatic way. It is based on the use of ontologies to 
manage the tags and provide advanced services of automatic learning, reasoning, etc. 


## Configuration example ##
Acota configuration files only could by written in Java properties (key=value), XML 
comming soon:
Example Coming Soon!

## How to use it? ##

```java
RequestSuggestionTO request = new RequestSuggestionTO();
	
ResourceTO resource = new ResourceTO();
resource.setDescription("WESO is a multidisciplinary research group from the Department of" +
	"Computer Science, Spanish Philology and Philosophy at the University of Oviedo, " +
	"The group is involved in semantic web research, education and technology transfer.");
resource.setLabel("About Web Semantics Oviedo");
resource.setUri("http://www.weso.es");
request.setResource(resource);

EnhancerAdapter luceneEnhancer = new LuceneEnhancer();
EnhancerAdapter openNLPEnhancer = new OpenNLPEnhancer();
EnhancerAdapter wordnetEnhancer = new WordnetEnhancer();
EnhancerAdapter googleEnhancer = new GoogleEnhancer();
EnhancerAdapter labelRecommenderEnhancer = new LabelRecommenderEnhancer();

luceneEnhancer.setSuccessor(openNLPEnhancer);
openNLPEnhancer.setSuccessor(wordnetEnhancer);
wordnetEnhancer.setSuccessor(googleEnhancer);
googleEnhancer.setSuccessor(labelRecommenderEnhancer);

SuggestionTO suggest = luceneEnhancer.enhance(request);


Map<String, Double> labels = suggest.getLabels();
```

## Download ##
The current version of acota is 0.3.2, you can download it from:
### For Maven Users
 * [acota-core-0.3.2.jar](http://156.35.82.101:7000/downloads/acota/0.3.2/core/acota-core-0.3.2.jar "Download acota-feedback-0.3.2.jar") - [POM File](http://156.35.82.101:7000/downloads/acota/0.3.2/core/acota-core-0.3.2.pom "Download acota-feedback-0.3.2.pom")
 * [acota-feedback-0.3.2.jar](http://156.35.82.101:7000/downloads/acota/0.3.2/feedback/acota-feedback-0.3.2.jar "Download acota-feedback-0.3.2.jar") - [POM File](http://156.35.82.101:7000/downloads/acota/0.3.2/feedback/acota-feedback-0.3.2.pom "Download acota-feedback-0.3.2.pom")

### For Non Maven Users
Acota-bundle includes all required dependancies:

 * [acota-bundle-0.3.2.jar](http://156.35.82.101:7000/downloads/acota/0.3.2/bundle/acota-bundle-0.3.2.jar "Download acota-bundle-0.3.2.jar")

### Old Versions
Acota 0.3.1:
 * [acota-core-0.3.1.jar](http://156.35.82.101:7000/downloads/acota/0.3.1/core/acota-core-0.3.1.jar "Download acota-feedback-0.3.1.jar") - [POM File](http://156.35.82.101:7000/downloads/acota/0.3.1/core/acota-core-0.3.1.pom "Download acota-feedback-0.3.1.pom")
 * [acota-feedback-0.3.1.jar](http://156.35.82.101:7000/downloads/acota/0.3.1/feedback/acota-feedback-0.3.1.jar "Download acota-feedback-0.3.1.jar") - [POM File](http://156.35.82.101:7000/downloads/acota/0.3.1/feedback/acota-feedback-0.3.1.pom "Download acota-feedback-0.3.1.pom")
 * [acota-bundle-0.3.1.jar](http://156.35.82.101:7000/downloads/acota/0.3.1/bundle/acota-bundle-0.3.1.jar "Download acota-bundle-0.3.1.jar")

Acota 0.3.0:
 * [acota-core-0.3.0.jar](http://156.35.82.101:7000/downloads/acota/0.3.0/core/acota-core-0.3.0.jar "Download acota-feedback-0.3.0.jar") - [POM File](http://156.35.82.101:7000/downloads/acota/0.3.0/core/acota-core-0.3.0.pom "Download acota-feedback-0.3.0.pom")
 * [acota-feedback-0.3.0.jar](http://156.35.82.101:7000/downloads/acota/0.3.0/feedback/acota-feedback-0.3.0.jar "Download acota-feedback-0.3.0.jar") - [POM File](http://156.35.82.101:7000/downloads/acota/0.3.0/feedback/acota-feedback-0.3.0.pom "Download acota-feedback-0.3.0.pom")

## Disclaimer
Acota-feedback requires a MySQL Database, you can download the SQL Creation Script from:
 * [ACOTA's Database SQL Dump](http://156.35.82.101:7000/downloads/acota/utils/acota.sql "ACOTA's Database SQL Dump")

Acota does not include Wordnet Dictionary or NLP Files, you can download it from:
 * [Wordnet 3.0](http://wordnetcode.princeton.edu/3.0/WNdb-3.0.tar.gz "Download Wordnet 3.0 Dict Files")
 * [SpanishPOS.bin](http://156.35.82.101:7000/downloads/acota/utils/SpanishPOS.bin "Download SpanishPOS.bin")
 * [SpanishSent.bin](http://156.35.82.101:7000/downloads/acota/utils/SpanishSent.bin "Download SpanishSent.bin")
