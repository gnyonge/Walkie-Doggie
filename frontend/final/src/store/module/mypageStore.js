import { rscApi } from '@/services/api';
const state = {
  dogInfo: [],
  

};
const getters = {
  getDogInfo(state) {
    return state.dogInfo
  },
  

};
const mutations = {
  setDogInfo(state,info) {
    state.dogInfo = info
  },

};
const actions = {
  // 지역 등록 
  getAddressInApi(context,params) {
    return rscApi.get('user/address',params)
    .then((res)=> {
      console.log(res,'주소 받아오기!!!')
      return res;
    })
  },
  // 유저 인포 불러오기 
  getUserInfoInApi(context,params) {
    return rscApi.get('user/info',params)
    .then((res)=> {
      console.log(res, '유저 인포 받아오기 !!')
      return res;
    })
  },
  // 반려견 등록 
  dogRegisterInApi(context, params) {
    return rscApi.post('pet/insert', params)
    .then ((res) => {
      console.log(res, '반려경 등록 성공!!')
      return res;
    })
    .catch(()=>{});
  },
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
