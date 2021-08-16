FROM node
EXPOSE 4200
RUN mkdir -p /build/libs/angularWeb
WORKdir /build/libs/angularWeb
COPY package.json package.json
RUN npm install --cache /tmp/empty-cache
RUN npm install -g @angular/cli@latest
COPY . .

CMD ["npm", "start"]
