# -*- coding: utf-8 -*-
"""
Created on Sat Sep 28 09:38:16 2019

@author: Nachiket
"""
import pyrebase as pb
config={"apiKey": "AIzaSyB4s-zOO50DyIduVkWWYb9c6c1DT97VBqg",
    "authDomain": "myshop-76cba.firebaseapp.com",
    "databaseURL": "https://myshop-76cba.firebaseio.com",
    "projectId": "myshop-76cba",
    "storageBucket": "",
    "messagingSenderId": "187498078910",
    "appId": "1:187498078910:web:29abd27a8f762f6b0174ef"
      }
import matplotlib.pyplot as plt
import numpy as np
from sklearn.cluster import KMeans
from math import radians, sin, cos, acos
import pandas as pd

from itertools import tee
df = pd.read_csv('C:/Users/Nachiket/Desktop/out.csv', sep=',')
x=df['Longitude'].values
y=df['Latitude'].values
z=df['fare_amount'].values
X=np.array(list(zip(x,y,z)))
#kmeans = KMeans(n_clusters=11)
#kmeans.fit(X)



i=0
while i<70:
    i=i+1
    firebase=pb.initialize_app(config)
    x=firebase.database().child("College").get()
    y=firebase.database().child("College")
    z=firebase.database().child("College")
    w=firebase.database().child("College")
    v=firebase.database().child("College")
    cc=[]
    
    ab=firebase.database()
    for u in x.each():
        cc.append(u.val())
    print(cc[0])
    if cc[0]==1:
        lat=y.child("mycor").child("lat").get().val()
        lon=z.child("mycor").child("long").get().val()
        myrange=v.child("mycor").child("range").get().val()
        slat=radians(lat)
        slon=radians(lon)
        #print(lat)
        #print(lon)
        ab.child("College").child("sample").remove()
        w.child("id").set(0)
        db=firebase.database().child("College")
        ab=firebase.database()
        
        count=0
        for l in X:
            dist = 6371.01 * acos(sin(slat)*sin(radians(l[1])) + cos(slat)*cos(radians(l[1]))*cos(slon - radians(l[0])))
            if dist<myrange:
                d={"lan":l[1],"lon":l[0],"price":l[2]}
                ab.child("College").child("sample").child("cor").child(count).set(d)
                count=count+1
                
        #firebase.database().child("College").child("sample").remove();
    if cc[0]==99:
        break
