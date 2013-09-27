#!/bin/sh

mvn -pl OpenESPI-DataCustodian-java install
mvn -pl OpenESPI-ThirdParty-java install
mvn -pl . clean verify
