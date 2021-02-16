import axios from "axios";
import { GET_ERRORS, GET_PROJECTS, GET_PROJECT_BY_ID } from "./types";

export const createProject = (project, history) => async (dispatch) => {
  try {
    const res = await axios.post("http://localhost:8080/projects", project);
    history.push("/dashboard");
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      // payload: error.response.data,
      payload: {},
    });
  }
};

export const getProjects = () => async (dispatch) => {
  const res = await axios.get("http://localhost:8080/projects");
  dispatch({
    type: GET_PROJECTS,
    payload: res.data,
  });
};

export const getProjectByID = (id, history) => async (dispatch) => {
  try {
    const res = await axios.get(`http://localhost:8080/projects/${id}`);
    dispatch({
      type: GET_PROJECT_BY_ID,
      payload: res.data,
    });
  } catch (error) {
    history.push("/dashboard");
  }
};
