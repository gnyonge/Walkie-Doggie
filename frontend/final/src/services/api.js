import axios from 'axios';
// import store from '../store/index';

const rscApi = axios.create({
  baseURL: 'http://localhost' + ':8888/pet',
});

// // Add a request interceptor
// rscApi.interceptors.request.use(
//   function(config) {
//     if (store.state.accessToken !== null) {
//       config['headers'] = {
//         'X-AUTH-ACCESS': store.getters.getAccessToken,
//       };
//     }
//     // Do something before request is sent
//     console.log('토큰이다', store.getters.getAccessToken);
//     return config;
//   },
//   function(error) {
//     // Do something with request error
//     return Promise.reject(error);
//   }
// );
export { rscApi };
