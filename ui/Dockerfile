#Stage 1
FROM node:16-alpine as build-step
WORKDIR /app
COPY . .
RUN npm install 
RUN npm run build

#Stage 2
FROM nginx:latest
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=build-step /app/dist/bookstore-springboot /usr/share/nginx/html