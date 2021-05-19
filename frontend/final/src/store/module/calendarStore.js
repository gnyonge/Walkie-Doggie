import { rscApi } from '@/services/api';

const state = {
  nowTab: 0, // BottomNav 탭
  detailBtn: '', // 다이어리 디테일 버튼
  selectedDate: "", // 선택된 날짜
  prettyDate: "", // 보여주기 위해 selectedDate 변환
  myDiaryObject: {}, // 다이어리 전체 객체
  myWalkObject: {}, // 산책기록 전체 객체
  foodResult: {}, // 성분분석 후 전체 객체
};
const getters = {
  getNowTab(state) {
    return state.nowTab
  },
  getDetailBtn(state) {
    return state.detailBtn
  },
  getSelectedDate(state) {
    return state.selectedDate
  },
  getPrettyDate(state) {
    return state.prettyDate
  },
  getMyDiaryObject(state) {
    return state.myDiaryObject
  },
  getMyWalkObject(state) {
    return state.myWalkObject
  },
  getFoodResult(state) {
    return state.foodResult
  },
};
const mutations = {
  setNowTab(state, now) {
    state.nowTab = now
  },
  setDetailBtn(state, data) {
    state.detailBtn = data
  },
  setSelectedDate(state, date) {
    state.selectedDate = date
  },
  setPrettyDate(state, date) {
    state.prettyDate = date
  },
  setMyDiaryObject(state, diary) {
    state.myDiaryObject = diary
  },
  setMyWalkObject(state, walk) {
    state.myWalkObject = walk
  },
  setFoodResult(state, data) {
    state.foodResult = data
  },
};
const actions = {
  // 사진없는 일기 작성
  createNoPhotoDiaryInApi(context, params) {
    return rscApi.post('diary/insert/np', params)
      .then((res) => {
        context.commit('setMyDiaryObject', res.data)
        return res;
      })
      .catch(() => {
        console.log('실패')
      })
  },
  // 사진있는 일기 작성
  createWithPhotoDiaryInApi(context, params) {
    return rscApi.post('diary/insert', params)
      .then((res) => {
        context.commit('setMyDiaryObject', res.data)
        return res;
      })
      .catch(() => {
        console.log('실패')
      })
  },
  // 일기 가져오기
  getTodayDiaryInApi(context, params) {
    return rscApi.get(`diary/${params.date}?peid=${params.peid}`)
      .then((res) => {
        context.commit('setMyDiaryObject', res.data)
        return res;
      })
      .catch(() => {
        console.log('실패')
      })
  },
  getTodayWalkInApi(context, params) {
    return rscApi.get(`diary/${params.date}/walk?peid=${params.peid}`)
      .then((res) => {
        context.commit('setMyWalkObject', res.data)
        return res;
      })
      .catch(() => {
        console.log('실패')
      })
  },
  // 사진없는 일기 수정
  updateNoPhotoDiaryInApi(context, params) {
    return rscApi.put('diary/update/np', params)
      .then((res) => {
        context.commit('setMyDiaryObject', res.data)
        return res;
      })
      .catch((error) => {
        console.log(error)
      })
  },
  // 사진있는 일기 수정
  updateWithPhotoDiaryInApi(context, params) {
    return rscApi.put('diary/update', params)
      .then((res) => {
        context.commit('setMyDiaryObject', res.data)
        return res;
      })
      .catch((error) => {
        console.log(error)
      })
  },
  // 일기 삭제
  deleteTodayDiaryInApi(context, params) {
    return rscApi.put('diary/delete', params)
      .then((res) => {
        context.commit('setMyDiaryObject', null)
        return res;
      })
      .catch((error) => {
        console.log(error)
      })
  },
  foodPhotoInApi(context, {formData, peid}) {
    return rscApi.post(`ocr?peid=${peid}`, formData)
    .then((res) => {
      context.commit('setFoodResult', res.data)
    }).catch((error) => {
      console.log(error)
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
