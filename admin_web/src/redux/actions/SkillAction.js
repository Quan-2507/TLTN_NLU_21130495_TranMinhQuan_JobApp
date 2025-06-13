import { GET_SKILL_DETAIL, GET_SKILL_LIST } from "../constants";
import { history } from "../../App";
import { skillService } from "../../services/SkillService";
import { notification } from "antd";


export const getSkillListAction = () => {
    return async (dispatch) => {
        try {
            const result = await skillService.getListSkill();
            if (result.status === 200) {
                dispatch({
                    type: GET_SKILL_LIST,
                    arrSkill: result.data
                })
            }
        } catch (error) {
            console.log('error', error);
        }
    }
}

export const getSkillIdAction = (id) => {
    return async (dispatch) => {
        try {
            const result = await skillService.getSkillById(id);
            dispatch({
                type: GET_SKILL_DETAIL,
                skillDetail: result.data.data
            })
        } catch (error) {
            console.log('error', error);
        }
    }
}


export const addSkillAction = (formData) => {
    return async (dispatch) => {
        try {
            const result = await skillService.createSkill(formData)
            console.log(result);
            if (result.data.statusCode === 200) {
                notification.success({
                    closeIcon: true,
                    message: 'Success',
                    description: (
                        <>Add new Skill successfully.</>
                    ),
                });
                history.push('/admin/skillmng');
            } else {
                notification.error({
                    closeIcon: true,
                    message: 'Error',
                    description: (
                        <>Add new Skill fail.</>
                    ),
                });
            }
        } catch (error) {
            console.log('error', error);
        }
    }
}

export const updateSkillByIdAction = (id, formData) => {
    return async (dispatch) => {
        try {
            const result = await skillService.updateSkill(id, formData);
            notification.success({
                closeIcon: true,
                message: 'Success',
                description: (
                    <>Update Skill successfully</>
                ),
            });
            history.push('/admin/skillmng');
        } catch (error) {
            console.log('error', error);
        }
    }
}

export const deleteSkillAction = (id) => {
    return async (dispatch) => {
        try {
            const result = await skillService.deleteSkill(id);
            notification.success({
                closeIcon: true,
                message: 'Success',
                description: (
                    <>Delete Skill successfully</>
                ),
            });
            dispatch(getSkillListAction())
        } catch (error) {
            console.log('error', error);
        }
    }
}