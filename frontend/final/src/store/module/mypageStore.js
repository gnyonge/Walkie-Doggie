import { rscApi } from '@/services/api';
const state = {
  dogInfo: [],
  allHealthList: [],

};
const getters = {
  getDogInfo(state) {
    return state.dogInfo
  },
  getAllHealthList(state) {
    return state.allHealthList
  }

};
const mutations = {
  setDogInfo(state,info) {
    state.dogInfo = info
  },
  setAllHealthList(state, list) {
    state.allHealthList = list
  }
};
const actions = {
  // 반려견 등록 
  dogRegisterInApi(context, params) {
    return rscApi.post('pet/insert', params)
    .then ((res) => {
      context.commit('setDogInfo', res.data);
      console.log(res.data, '반려견 등록 성공!!')
      return res;
    })
    .catch(()=>{});
  },
  // 반려견 정보 보기
  showDogInfoInApi(context, params) {
    return rscApi.get(`pet/show?peid=${params}`)
    .then ((res)=> {
      context.commit('setDogInfo',res.data);
      console.log(res,'반려견정보보기')
      return res;
      // get이라면 다르게 해줘야 한다. 
    })
    .catch(()=>{});
  },
  allHealthListInApi(context, params) {
    return rscApi.get(`pet/health/${params}`)
    .then((res) => {
      context.commit('setAllHealthList', res.data.listHealth)
    }).catch((error)=> {
      console.error(error)
    })
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
