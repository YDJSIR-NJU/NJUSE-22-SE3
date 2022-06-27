FROM node:14.18-stretch as build-stage
WORKDIR /app
COPY package*.json ./
RUN npm install -g cnpm --registry=https://registry.npm.taobao.org
RUN cnpm install
COPY ./ .
RUN cnpm run build

FROM nginx as production-stage
RUN mkdir /app
COPY --from=build-stage /app/dist /app
COPY nginx.conf /etc/nginx/nginx.conf
COPY cert/se3.ydjsir.com.cn.pem /etc/nginx/se3.ydjsir.com.cn.pem
COPY cert/se3.ydjsir.com.cn.key /etc/nginx/se3.ydjsir.com.cn.key

CMD nginx
