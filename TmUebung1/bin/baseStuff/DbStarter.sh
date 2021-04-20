sudo mkdir /run/postgresql
sudo chown outeh /run/postgresql
postgres -D '/home/outeh/Databases/postgres' &
sudo '/opt/lampp/lampp' start