# stage 1

# FROM node:alpine AS website
# EXPOSE 4200
# WORKDIR /src/build/libs/angularWeb
# COPY . .
# RUN npm ci && npm run build

# stage 2

# FROM nginx:alpine
# COPY --from=website /src/build/libs/angularWeb /usr/share/nginx/html
# EXPOSE 80


FROM node
EXPOSE 4200
RUN mkdir -p /src/build/libs/angularWeb
WORKdir /src/build/libs/angularWeb
COPY package.json package.json
RUN npm install --cache /tmp/empty-cache
RUN npm install -g @angular/cli@latest
COPY . .

CMD ["npm", "start"]
