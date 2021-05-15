import { rscApi } from '@/services/api';
const state = {
  userInfo: {}, //강아지정보 포함
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
  // 지역 등록 
  // getAddressInApi(context,params) {
  //   return rscApi.get('user/address',params)
  //   .then((res)=> {
  //     console.log(res,'주소 받아오기!!!')
  //     return res;
  //   })
  // },
  // 유저 인포 불러오기 (강아지정보 포함)
  getUserInfoInApi(context, params) {
    return rscApi.get(`user/info?uid=${params}`)
    .then((res)=> {
      console.log(res, '유저 인포 받아오기 !!')
      return res;
    })
  },
  // 반려견 등록 사진있음
  dogRegisterInApi(context, params) {
    return rscApi.post('pet/insert', params)
    .then ((res) => {
      context.commit('setDogInfo', res.data);
      console.log(res.data, '반려견 등록 성공!!')
      return res;
    })
    .catch(()=>{});
  },
  // 반려견 등록 사진없음
  NoPhotoDogRegisterInApi(context, params) {
    return rscApi.post('pet/insert/np', params)
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
      context.commit('setDogInfo', res.data);
      console.log(res,'반려견정보보기')
      return res;
    })
    .catch(()=>{});
  },
  // 반려견 정보 수정 사진 있음
  updateDogInfoInApi(context, params) {
    return rscApi.put('pet/update', params)
    .then ((res)=> {
      context.commit('setDogInfo', res.data);
      console.log(res.data,'반려견 수정완료 후 정보보기', state.dogInfo)
      return res;
    })
    .catch(()=>{});
  },
  // 반려견 정보 수정 사진추가없음
  NoPhotoUpdateDogInfoInApi(context, params) {
    return rscApi.put('pet/update/np', params)
    .then ((res)=> {
      context.commit('setDogInfo', res.data);
      console.log(res.data,'반려견 수정완료 후 정보보기', state.dogInfo)
      return res;
    })
    .catch(()=>{});
  },
  // 마이페이지 메인 > 건강
  allHealthListInApi(context, params) {
    return rscApi.get(`pet/health/${params}`)
    .then((res) => {
      context.commit('setAllHealthList', res.data.listHealth)
    }).catch((error)=> {
      console.error(error)
    })
  },
  // 반려견 정보 삭제
  deletePetInApi(context, params) {
    return rscApi.put('pet/leave', params)
    .then((res) => {
      context.commit('setDogInfo', null)
      console.log(res, '펫 삭제 완료')
    })
    .catch((error) => {
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
