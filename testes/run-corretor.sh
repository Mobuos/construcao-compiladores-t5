#!/bin/bash

corretor=$PWD/testes/corretor-automatico.jar
executavel=$PWD/t4-semantico/target/t4-semantico-1.0-SNAPSHOT-jar-with-dependencies.jar
pastaTemp=$PWD/temp
casosTeste=$PWD/testes/casos-de-teste
ras="790716, 791964, 790035"
opcao="t3,t4"

if [ ! -d $pastaTemp ]
then
    mkdir $pastaTemp
fi

java -jar $corretor "java -jar $executavel" gcc $pastaTemp $casosTeste "$ras" "$opcao"