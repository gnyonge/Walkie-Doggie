import { rscApi } from '@/services/api';

const state = {
  dog: {
    // 멍플레이스 
    selectedItem: null ,
  },
  like: {
    // 좋아요 포스팅 
    nowLat: 0,
    nowLon: 0,
    tempPhotoUrl: '',
    // 지역설정 관련
    startArea: '', 
    areaName: '', 
  },
  // 이동 경로 
  path: [],
};
const getters = {
  // 멍플레이스 
  getSelectedItem(state) {
    return state.dog.selectedItem
  },
  // 좋아요 포스팅 
  getNowLat(state){
    return state.like.nowLat
  },
  getNowLon(state){
    return state.like.nowLon
  },
  getTempPhotoURL(state){
    return state.like.tempPhotoUrl
  },
  getFirstAreaName(state){
    return state.like.startArea
  },
  getAreaName(state){
    return state.like.areaName
  },
  getMyPath(state){
    return state.path
  },
};
const mutations = {
  // 멍플레이스 
  setSelectedItem(state, data){
    state.like.selectedItem = data
  },
  // 좋아요 포스팅 
  setNowLat(state, data){
    state.like.nowLat = data
  },
  setNowLon(state, data){
    state.like.nowLon = data 
  },
  // 임시 사진 url 
  setTempPhotoURL(state, data){
    state.like.tempPhotoUrl = data 
  },
  // 산책시작 지역정보 
  setFirstAreaName(state, data){
    state.like.startArea = data
  },
  // 지역정보
  setAreaName(state, data){
    state.like.areaName = data
  },
  // 내가 간 경로 
  setMyPath(state, data){
    state.path.push(data)
  },
 
};
const actions = {
  // 멍플레이스 리스트 가져오기
  getPostsInApi(){
    // return rscApi.
  },
  // 업로드 사진 미리보기 
  makeTempPhotoUrlInApi(context, params){
    return rscApi.post('place/imageUpload', params)
      .then((res)=> {
        // tempPhotoURL 갱신 
        context.commit('setTempPhotoURL', res.data.url)
      })
  },
  // 좋아요 사진, 위도, 경도, 의견 백엔드 전송
  sendNowPostInApi(context, params){
    console.log(params)
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
  doneWalkInApi(context, params){
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
