@echo off

for %%i in (third-party-server registration-server zuul-server voucher-service gallery-service sms-service secure-service) do (
     cd %%i
     mvn clean package
     cd ..
)