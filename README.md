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

EnhancerAdapter enhancer1 = new LuceneEnhancer();
EnhancerAdapter enhancer2 = new OpenNLPEnhancer();
EnhancerAdapter enhancer3 = new WordnetEnhancer();
EnhancerAdapter enhancer4 = new GoogleEnhancer();
EnhancerAdapter enhancer5 = new LabelRecommenderEnhancer();

enhancer1.setSuccessor(enhancer2);
enhancer2.setSuccessor(enhancer3);
enhancer3.setSuccessor(enhancer4);
enhancer4.setSuccessor(enhancer5);

SuggestionTO suggest = enhancer1.enhance(request);


SuggestionTO suggest = enhacer.enhance(request);
```
