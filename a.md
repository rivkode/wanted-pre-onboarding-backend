version: '3'

services:
db:
container_name: preonb_db
image: mysql:8
volumes:
- db:/var/lib/mysql
restart: unless-stopped
environment:
- MYSQL_ROOT_PASSWORD=root
- MYSQL_DATABASE=preonb
#      - MYSQL_USER=root
#      - MYSQL_PASSWORD=root
    ports:
      - "3307:3306"
    networks:
      - preon_net

preonb:
container_name: preonb
depends_on:
- db
image: preonb:latest
ports:
- "8081:8081"
restart: unless-stopped
environment:
PREONB_DB_HOST: preonb_db:3307
PREONB_DB_USER: root
PREONB_DB_PASSWORD: root
PREONB_DB_NAME: preonb
networks:
- preon_net

volumes:
db: {}

networks:
preon_net: {}
