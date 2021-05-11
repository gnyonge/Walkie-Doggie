import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate';

import calendar from './module/calendarStore';
import walk from './module/walkStore';
import mypage from './module/mypageStore';
import food from './module/foodStore';
import loginSignin from './module/loginSigninStore';

Vue.use(Vuex)

export default new Vuex.Store({
  modules: { calendar, walk, mypage, food, loginSignin },
  plugins: [
    createPersistedState({
      paths: ['calendar', 'walk', 'mypage', 'food', 'loginSignin']
    })
  ],
})
