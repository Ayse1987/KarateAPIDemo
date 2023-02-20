function fn() {

     var config = {
          regresIn: 'https://reqres.in',
          dataGenerator: Java.type('com.regresIn.utilities.DataGenerator'),
          generateUUID: Java.type('com.regresIn.utilities.UUIDGenerator'),
      }

//      karate.configure('connectTimeout', 5000);
//      karate.configure('readTimeout', 5000);
      karate.configure('logPrettyRequest', true);
      karate.configure('logPrettyResponse', true);
      karate.configure('ssl', true);

     return config;
  }