import { rscApi } from '@/services/api';
const state = {
  token: '',
  user: {},
};
const getters = {
  getToken(state) {
    return state.token
  },
  getUser(state) {
    return state.user
  }
};
const mutations = {
  setToken(state,token) {
    state.token = token
  },
  setUser(state,user) {
    state.user = user
  }

};
const actions = { 
  // 로그인 
  // - 자체 로그인 
  loginNormalInApi({commit, state}, params) {
    return rscApi.post('login/signin',params)
    .then ((res)=> {
      commit('setToken',res.data.doggie_token);
      commit('setUser',res.data.user);
      console.log('로그인완료', res.data.user)
      console.log('유저되나요', state.user, state.token)
      return res.data.message;
      // get이라면 다르게 해줘야 한다. 
    })
    .catch((error)=>{
      console.log(error)
    });
  },

  // 회원가입 
  createUserInApi(context,params) {
    return rscApi.post('user/signup',params)
    .then ((res) => {
      console.log(res)
      context.commit('setUser',res.data);
      console.log(res, '회원가입')
      return res;
    })
    .catch(()=>{});
  },
  
  // 인증메일 
  sendEmailInApi(context,params) {
    return rscApi.post('user/sendMail',params)
    .then ((res) => {
      console.log(res, '메일전송')
      return res;
    })
    .catch(()=>{});
  },

  // 인증 번호 확인 
  checkAuthEmailInApi(context,params) {
    return rscApi.post('user/checkMail',params)
    .then ((res) => {
      return res;
    })
    .catch(()=>{});
  },
  
  // 비밀번호 확인 
  checkPasswordInApi(context,params) {
    return rscApi.post('user/check/pass',params)
    .then ((res) => {
      return res;
    })
    .catch(()=>{});
  }
};

export default {
  state: {
    ...state,
  },
  getters,
  mutations,
  actions,
};
