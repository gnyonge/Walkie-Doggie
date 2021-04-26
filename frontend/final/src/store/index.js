import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate';

Vue.use(Vuex)

export default new Vuex.Store({
  plugins: [
    createPersistedState()
  ],
  state: {
    nowTab: 0,
    selectedDate: ""
  },
  getters: {
    getNowTab(state) {
      return state.nowTab
    },
    getSelectedDate(state) {
      return state.selectedDate
    }
  },
  mutations: {
    setNowTab(state, now) {
      state.nowTab = now
    },
    setSelectedDate(state, date) {
      state.selectedDate = date
    }
  },
  actions: {
  },
})
