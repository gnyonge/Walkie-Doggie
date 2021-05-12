import { rscApi } from '@/services/api';
const state = {
  dogInfo: []
};
const getters = {
};
const mutations = {
  setDogInfo(state,info) {
    state.dogInfo = info
  }
};
const actions = {
  // 반려견 등록 
  dogRegisterInApi(context,params) {
    return rscApi.post('pet/insert',params)
    .then ((res) => {
      return res;
    })
    .catch(()=>{});
  },
  //user 정보
  // dogRegisterInApi(context) {
  //   return rscApi.get('pet/insert')
  //   .then ((res) => {
  //     return res;
  //   })
  //   .catch(()=>{});
  // },

  // 반려견 정보 보기
  showDogInfoInApi(context,params) {
    return rscApi.get('pet/show',params)
    .then ((res)=> {
      context.commit('setDogInfo',res.data);
      console.log(res,'반려견정보보기')
      return res;
      // get이라면 다르게 해줘야 한다. 
    })
    .catch(()=>{});
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
