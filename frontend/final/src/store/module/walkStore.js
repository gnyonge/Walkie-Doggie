import { rscApi } from '@/services/api';

const state = {
  // 멍플레이스 
  selectedItem: null ,
  // 좋아요 포스팅 
  nowLat: 0,
  nowLon: 0,
  tempPhotoUrl: '',
  areaName: '장덕동', 
  
};
const getters = {
  getSelectedItem(state) {
    return state.selectedItem
  },
  getNowLat(state){
    return state.nowLat
  },
  getNowLon(state){
    return state.nowLon
  },
  getTempPhotoURL(state){
    return state.tempPhotoUrl
  },
  getAreaName(state){
    return state.areaName
  }
};
const mutations = {
  // 멍플레이스 
  setSelectedItem(state, data){
    state.selectedItem = data
  },

  // 좋아요 포스팅 
  setNowLat(state, data){
    state.nowLat = data
  },
  setNowLon(state, data){
    state.nowLon = data 
  },
  // 임시 사진 url 
  setTempPhotoURL(state, data){
    state.tempPhotoUrl = data 
  },
  // 지역정보
  setAreaName(state, data){
    state.areaName = data
  }
};
const actions = {
  // 멍플레이스 리스트 가져오기
  getPostsInApi(){
    // return rscApi.
  },
  // 업로드 사진 미리보기 
  makeTempPhotoUrlInApi(context, params){
    return rscApi.post('place/imageUpload', params, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
      .then((res)=> {
        // tempPhotoURL 갱신 
        context.commit('setTempPhotoURL', res.url)
        console.log(res.url, '여기요')
      })
      .catch((error) => {
        console.error(error,'에러')
      })
  },
  // 좋아요 사진, 위도, 경도, 의견 백엔드 전송
  sendNowPostInApi(params){
    return rscApi.post('place/likePlace', params)
      .then((res) =>{
        console.log('좋아요 포스팅 성공')
        console.log(res)
      }).catch((error) =>{
        console.log('포스팅 실패')
        return error
      })
  },
  // 산책 종료 
  doneWalkInApi(params){
    return rscApi.post('walk/insert', params)
      .then((res) => {
        console.log(res)
      }).catch((error) =>{
        console.log(error)
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
