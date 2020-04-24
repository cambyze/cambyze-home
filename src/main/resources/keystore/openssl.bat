openssl pkcs12 -export -in cambyze.com_ssl_certificate.cer -inkey cambyze.com_private_key.key -out .keystore -name tomcat -CAfile cambyze.com_ssl_certificate.cer -caname root

keytool -list -v