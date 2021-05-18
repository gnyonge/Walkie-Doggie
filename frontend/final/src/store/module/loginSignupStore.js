import { rscApi } from '@/services/api';
const state = {
  token: '',
  user: {}, //강아지정보 미포함
  add: ''
};
const getters = {
  getToken(state) {
    return state.token
  },
  getUser(state) {
    return state.user
  },
  getAddress(state) {
    return state.add
  }
};
const mutations = {
  setToken(state,token) {
    state.token = token
  },
  setUser(state,user) {
    state.user = user
  },
  setAddress(state,add) {
    state.add = add
  }
};
const actions = { 
  // 로그인 
  // - 자체 로그인 
  loginNormalInApi({commit}, params) {
    return rscApi.post('login/signin',params)
    .then ((res)=> {
      commit('setToken',res.data.doggieToken);
      commit('setUser',res.data.user);
      sessionStorage.setItem('doggieToken',res.data.doggieToken);
      return res.data.message;
      // get이라면 다르게 해줘야 한다. 
    })
    .catch((error)=>{
      console.error(error)
    });
  },

  // 회원가입 
  createUserInApi(context,params) {
    return rscApi.post('user/confirm/signup',params)
    .then ((res) => {
      return res;
    })
    .catch(()=>{});
  },
  
  // 인증메일 
  sendEmailInApi(context,params) {
    return rscApi.post('user/confirm/sendMail',params)
    .then ((res) => {
      return res;
    })
    .catch(()=>{});
  },

  // 인증 번호 확인 
  checkAuthEmailInApi(context,params) {
    return rscApi.post('user/confirm/checkMail',params)
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
  },

  // 비밀번호 변경
  changePasswordInApi(context,params) {
    return rscApi.put('user/change/pass',params)
    .then ((res) => {
      return res;
    })
    .catch(()=>{});
  },
 
  // 지역등록
  setAddressInApi(context,params) {
    return rscApi.get(`user/address?add=${params.add}&uid=${params.uid}`)
    .then ((res) => {
      context.commit('setUser', res.data.user)
      return res;
    })
    .catch(()=>{});
  },

   // 회원탈퇴
   deleteUserInApi(context, params) {
    return rscApi.put('user/leave', params)
    .then(() => {
      context.commit('setUser', null)
    })
    .catch((error) => {
      console.error(error)
    })
  },
};

export default {
  state: {
    ...state,
  },
  getters,
  mutations,
  actions,
};
