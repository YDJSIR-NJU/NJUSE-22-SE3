FROM python:3.9

WORKDIR /usr/src/app

COPY requirements.txt ./
RUN pip install -i https://mirrors.cloud.tencent.com/pypi/simple --no-cache-dir -r requirements.txt
RUN pip install -i https://mirrors.cloud.tencent.com/pypi/simple torch

COPY . .
RUN python3 setup.py install

CMD gunicorn -b 0.0.0.0:5000 app:app --timeout 600 --log-level debug
#CMD flask run -p 5000 --host=0.0.0.0
