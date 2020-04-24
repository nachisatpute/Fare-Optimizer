import numpy as np 
import pandas as pd 
import matplotlib.pyplot as plt
import seaborn as sns


import os


data=pd.read_csv("D:/train.csv",nrows = 10000)
data.columns
cols = ['fare_amount', 'pickup_datetime', 'pickup_longitude', 'pickup_latitude', 'dropoff_longitude', 'dropoff_latitude', 'passenger_count']
data=pd.read_csv("D:/train.csv",nrows = 100000,usecols=cols)
print(data.columns)
print(data.shape)
data.head(5)
data.describe()
plt.scatter(x=data.fare_amount,y=data.index)
plt.ylabel('Index')
plt.xlabel('fare_amount')
plt.show()
data['fare_amount'][(data.fare_amount<0) | (data.fare_amount>=300)].count()

data.shape

data=data.drop(data[(data.fare_amount<0) | (data.fare_amount>=300)].index,axis=0)

data.fare_amount.isnull().sum()

data.shape
data.isnull().sum()
print(data.pickup_latitude.head(5))
print(data.dropoff_latitude.head(5))
test_data=pd.read_csv('D:/test1.csv')
test_data.head(5)
test_data=pd.read_csv('D:/test1.csv')
test_data.head(5)

test_data.columns

lon_min=min(test_data.pickup_longitude.min(),test_data.dropoff_longitude.min())
lon_max=max(test_data.pickup_longitude.max(),test_data.dropoff_longitude.max())
print(lon_min,',',lon_max)


lat_min=min(test_data.pickup_latitude.min(),test_data.dropoff_latitude.min())
lat_max=max(test_data.pickup_latitude.max(),test_data.dropoff_latitude.max())
print(lat_min,',',lat_max)



data[(data.pickup_latitude< lat_min) | (data.pickup_latitude>lat_max) ]

data.drop(data[(data.pickup_latitude< lat_min) | (data.pickup_latitude>lat_max)].index,axis=0,inplace=True)

data[(data.dropoff_latitude< lat_min) | (data.dropoff_latitude>lat_max)]


data.drop(data[(data.dropoff_latitude<lat_min) | (data.dropoff_latitude>lat_max)].index,axis=0,inplace=True)


print(data.pickup_longitude.tail(5))
print(data.dropoff_longitude.tail(5))

data.pickup_longitude.abs().head()

data[(data.pickup_longitude<lon_min) | (data.pickup_longitude>lon_max)]

data.drop(data[(data.pickup_longitude<lon_min) | (data.pickup_longitude>lon_max)].index,axis=0,inplace=True)

data[(data.dropoff_longitude<lon_min) | (data.dropoff_longitude>lon_max)]

data.drop(data[(data.dropoff_longitude<lon_min) | (data.dropoff_longitude>lon_max)].index,axis=0,inplace=True)


data.isnull().sum()

data[data.dropoff_longitude.isnull()==True].head(1)

data.drop(data[data.dropoff_longitude.isnull()==True].index,axis=0,inplace=True)

data.isnull().sum()


data.describe()


plt.scatter(x=data.passenger_count,y=data.index)
plt.ylabel('Index')
plt.xlabel('passenger_count')
plt.show()

data['passenger_count'][data.passenger_count==0].count()

print(data.shape)
data.drop(data[data.passenger_count==0].index,axis=0,inplace=True)
print(data.shape)

print(data.shape)
data.drop(data[data.passenger_count>8].index,axis=0,inplace=True)
print(data.shape)
data.to_csv('D:/outputfinal.csv')



data['pickup_datetime'].head(1)


data['pickup_datetime']=pd.to_datetime(data['pickup_datetime'], infer_datetime_format=True)

data['pickup_datetime'].head(1)

data['year'] = data['pickup_datetime'].dt.year
data['Month'] = data['pickup_datetime'].dt.month
data['Date'] = data['pickup_datetime'].dt.day
data['Day of Week'] = data['pickup_datetime'].dt.dayofweek
data['Hour'] = data['pickup_datetime'].dt.hour
data['Minute'] = data['pickup_datetime'].dt.minute



from math import radians, cos, sin, asin, sqrt
def haversine(a):
    lon1=a[0]
    lat1=a[1]
    lon2=a[2]
    lat2=a[3]

    lon1, lat1, lon2, lat2 = map(radians, [lon1, lat1, lon2, lat2])

    dlon = lon2 - lon1 
    dlat = lat2 - lat1 
    a = sin(dlat/2)**2 + cos(lat1) * cos(lat2) * sin(dlon/2)**2
    c =  2 * asin(sqrt(a)) 
    km = 6371* c
    return km
# 1min 

data['distance']=data[['pickup_longitude','pickup_latitude','dropoff_longitude','dropoff_latitude']].apply(haversine,axis=1)

data["distance"].head(5)

#Analyze column distance ,and drop the rows if necessary to drop
data[data.distance==0].shape  # found records with 0 distnace 

print(data.shape)
data.drop(data[data.distance==0].index,axis=0, inplace=True) # drop the rows having records of distance as 0
print(data.shape)

plt.figure(figsize=(15,7))
plt.scatter(x=data['Date'], y=data['fare_amount'], s=1.5)
plt.xlabel('Date')
plt.ylabel('Fare')

plt.figure(figsize=(15,7))
plt.hist(data['Hour'], bins=100)
plt.xlabel('Hour')
plt.ylabel('Frequency')

plt.figure(figsize=(15,7))
plt.scatter(x=data['Hour'], y=data['fare_amount'], s=1.5)
plt.xlabel('Hour')
plt.ylabel('Fare')


plt.figure(figsize=(15,7))
plt.hist(data['passenger_count'], bins=15)
plt.xlabel('No. of Passengers')
plt.ylabel('Frequency')

plt.figure(figsize=(15,7))
plt.scatter(x=data['passenger_count'], y=data['fare_amount'], s=1.5)
plt.xlabel('No. of Passengers')
plt.ylabel('Fare')

plt.figure(figsize=(15,7))
plt.hist(data['Day of Week'], bins=100)
plt.xlabel('Day of Week')
plt.ylabel('Frequency')
test_data=pd.read_csv("D:/test1.csv")
test_data.head()

test_data.dtypes

test_data['key']=pd.to_datetime(test_data['key'], infer_datetime_format=True)
test_data['pickup_datetime']=pd.to_datetime(test_data['pickup_datetime'], infer_datetime_format=True)

test_data.dtypes

test_data['year'] = test_data['pickup_datetime'].dt.year
test_data['Month'] = test_data['pickup_datetime'].dt.month
test_data['Date'] = test_data['pickup_datetime'].dt.day
test_data['Day of Week'] = test_data['pickup_datetime'].dt.dayofweek
test_data['Hour'] = test_data['pickup_datetime'].dt.hour
test_data['Minute'] = test_data['pickup_datetime'].dt.minute

test_data.head(3)

test_data.isnull().sum()

from math import radians, cos, sin, asin, sqrt,atan2
def haversine(a):
    lon1=a[0]
    lat1=a[1]
    lon2=a[2]
    lat2=a[3]

    # convert decimal degrees to radians 
    lon1, lat1, lon2, lat2 = map(radians, [lon1, lat1, lon2, lat2])
    # haversine formula 
    dlon = lon2 - lon1 
    dlat = lat2 - lat1 
    a = sin(dlat/2)**2 + cos(lat1) * cos(lat2) * sin(dlon/2)**2
    c = 2 * asin(sqrt(a)) 
    km = 6371* c
    return km


test_data['distance']=test_data[['pickup_longitude','pickup_latitude','dropoff_longitude','dropoff_latitude']].apply(haversine,axis=1)

test_data['distance'].describe()


test_data.columns


data.dtypes

print(data.shape)
data=data.drop('pickup_datetime',axis=1)

print(data.shape)

data.dtypes

data.iloc[:,:].head(1)

X=data.drop("fare_amount",axis=1)
y=data.iloc[:,0].values
print(y)

X.shape

y.shape


test_data

X_test=test_data
print(X_test.columns)
print(X_test.shape)
print(X_test.dtypes)

X_test.drop(["key","pickup_datetime"],axis=1,inplace=True)

print(X_test.shape)
print(X_test.dtypes)

import xgboost
Regression_xgbooost =xgboost.XGBRegressor(n_estimators=50,max_depth=15,max_leaves=9,random_state=0)
Regression_xgbooost.fit(X,y)
y_test_pred=Regression_xgbooost.predict(X_test)
print(y_test_pred)

submission = pd.read_csv('D:/sample_submission.csv')
submission['fare_amount'] = y_test_pred
submission.to_csv('D:/submitfilefinal.csv', index=False)
submission

from sklearn.model_selection import train_test_split
train_X, val_X, train_y, val_y =train_test_split(X,y,test_size=0.2,random_state=0)

import xgboost
Regression_xgbooost =xgboost.XGBRegressor(n_estimators=300,max_depth=15,max_leaves=9,random_state=0)
Regression_xgbooost.fit(train_X,train_y)
y_pred=Regression_xgbooost.predict(val_X)

from sklearn.metrics import mean_absolute_error
print(mean_absolute_error(val_y,y_pred))