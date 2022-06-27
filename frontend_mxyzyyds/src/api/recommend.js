import axios from 'axios'
import {RECOMMEDN_MODULE, updateToken} from './_prefix'

export const getRecommendations = (uid) => {
  return axios.get(`${RECOMMEDN_MODULE}/getRecommendations?uid=${uid}`,
    {
      headers: {
        // Authorization: store.state.token
        Authorization: window.localStorage.getItem("token")
      }
    }
  ).then(response => {
      updateToken(response)
      return response
    }
  )
}

export const changeStrategy = (strategyId) => {
  return axios.get(`${RECOMMEDN_MODULE}/changeStrategy?strategyId=${strategyId}`,
    {
      headers: {
        // Authorization: store.state.token
        Authorization: window.localStorage.getItem("token")
      }
    }
  ).then(response => {
    updateToken(response)
    return response
  })
}

export const modifyStrategy = (config, strategyId, N) => {
  console.log(config)
  return axios.post(`${RECOMMEDN_MODULE}/modifyStrategy?strategyId=${strategyId}&N=${N}`, config,
    {
      headers: {
        // Authorization: store.state.token
        Authorization: window.localStorage.getItem("token")
      }
    }
  ).then(response => {
    updateToken(response)
    return response
  })
}

export const getStrategies = () => {
  return axios.get(`${RECOMMEDN_MODULE}/getStrategies`,
    {
      headers: {
        // Authorization: store.state.token
        Authorization: window.localStorage.getItem("token")
      }
    }
  ).then(response => {
    updateToken(response)
    return response
  })
}
