import axios from 'axios';
// import store from '../store/index';


const rscApi = axios.create({
  baseURL: 'http://localhost' + ':8888/pet',
  // headers: {
  //   'doggie_token': sessionStorage.getItem('doggie_token') // header의 속성
  // }
});

// Add a request interceptor
rscApi.interceptors.request.use(
  function(config) {
    var token = sessionStorage.getItem('doggie_token');
    if (token !== null) {
      config['headers'] = {
        'doggie_token': token,
      };
    }
    // Do something before request is sent
    // console.log('토큰이다', token);
    return config;
  },
  function(error) {
    // Do something with request error
    return Promise.reject(error);
  }
);



export { rscApi };
