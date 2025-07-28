@REM @echo off
call set DB_HOST=localhost
call set BASE_URL=http://localhost:8080
call set PORT_APP=8080
call set DB_PORT=5438
call set DB_NAME=microservicesDB
call set DB_USER=jasperApp
call set DB_PASSWORD=jasperApp
call set "JWT_SECRET=JOBPORTAL-RFsAfqfwq-+d8RYCPiC~&Q_E?^Z1dxq|{k.dsfadsfgeh78ffffMc& I-*TZb(0:8=m2m}SEOH6v)`R.XHQZ"
call set "JWT_ACTIVATION=ayLGrXGXho8qw1Wo7sWXgtrVqCZLBXSWre02uwxtRfId3o3womF9IJYuRzCYVyZa"
call set "JWT_UPDATE_PROFILE=J4R34rjwkKEgKcaZlsSHuA5SoHXv4woKoDDG1gykpT78tqyrT2xcQCPV4U1rdb5S"
call set JWT_ISSUE=VehicleBucket
call set "MAIL_HOST_DATA=smtp.gmail.com"
call set MAIL_PORT=587
call set MAIL_USERNAME=sellonepython@gmail.com
call set MAIL_PASSWORD=vgcstyozwxjmeylk
call set "MONGO_DB=mongodb+srv://vernover:noverver@database-course-cluster.0ahanag.mongodb.net/KendaraanDB?retryWrites=true&w=majority&appName=database-course-Cluster"
call set "DB_MONGO=KendaraanDB"
call set REDIS_HOST=localhost
call set REDIS_PORT=6379


@REM for /f "usebackq tokens=1,* delims==" %%A in (".env.example") do (
@REM     call set %%A=%%B
@REM )
mvn clean install
@REM java -jar target\BackaplikasiKendaraan.jar
@REM pause

@REM https://id.jobstreet.com/id/job/85633507?token=1~91076b19-1d45-47df-8e4c-056f7914dec1&ref=post-apply-recs