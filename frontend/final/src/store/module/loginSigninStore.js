import { rscApi } from '@/services/api';
const state = {
};
const getters = {
};
const mutations = {
  
};
const actions = {
  // 로그인 
  // - 자체 로그인 
  loginNormalInApi(context, params) {
    return rscApi.post('login/signin',params)
    .then ((res)=> {
      // context.commit('setSelectedDate',res.data);
      return res;
      // get이라면 다르게 해줘야 한다. 
    })
    .catch(()=>{});
  },
  // 회원가입 
  createUserInApi(context,params) {
    return rscApi.post('user/signup',params)
    .then ((res) => {
      return res;
    })
    .catch(()=>{});
  },
  // 인증메일 
  sendEmailInApi(context,params) {
    return rscApi.post('user/sendMail',params)
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
