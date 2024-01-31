# shinhan_maah

### docker sql 이미지, 컨테이너 생성. 기존 컨테이너 명과 겹치지 않게 주의. 이전 컨테이너 삭제
docker run -p 5432:5432 --name postgres_docker -dit starrfnl/maah:0.2
