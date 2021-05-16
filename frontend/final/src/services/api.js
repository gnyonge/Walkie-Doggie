import axios from 'axios';
// import store from '../store/index';


const rscApi = axios.create({
  baseURL: 'http://localhost' + ':8888/pet',
});

// Add a request interceptor
rscApi.interceptors.request.use(
  function(config) {
    if (this.$store.state.token !== null) {
      config['headers'] = {
        'doggie-token': this.$store.state.token,
      };
    }
    // Do something before request is sent
    console.log('토큰이다', this.$store.state.token);
    return config;
  },
  function(error) {
    // Do something with request error
    return Promise.reject(error);
  }
);

export { rscApi };
