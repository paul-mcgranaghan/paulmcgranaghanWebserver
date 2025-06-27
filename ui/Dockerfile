FROM node:24.2.0-slim as node

WORKDIR /usr/src/app

COPY package.json .
COPY package-lock.json .

RUN npm install
COPY . .

EXPOSE 4200

CMD /usr/src/app/node_modules/.bin/ng serve --host 0.0.0.0 --disable-host-check
#RUN npm run build

# Stage 2
#FROM nginx:1.13.12-alpine
#COPY --from=node /ui/dist/ui /usr/share/nginx/html
#COPY ./nginx.conf /etc/nginx/conf.d/default.conf
