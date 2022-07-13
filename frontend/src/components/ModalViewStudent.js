//
import React, { useEffect, useState } from "react";
import { Descriptions, List, Avatar } from "antd";
import { Button, Form, Input } from "antd";
import axios from "axios";
import jsonwebtoken from "jsonwebtoken";

const ModalViewStudent = ({ student, keycloak }) => {
  let token = jsonwebtoken.decode(keycloak.token);

  let address = student.address ? JSON.parse(student.address) : {};
  const [comments, setComments] = useState([]);
  async function fetchComments() {
    console.log(
      `${process.env.REACT_APP_BACKEND_BASE_URL}/studentcomment/${student.username}`
    );
    try {
      let res = await axios.get(
        `${process.env.REACT_APP_BACKEND_BASE_URL}/studentcomment/${student.username}`,
        {
          headers: {
            // Authorization: `Bearer ${keycloak.token}`
          },
        }
      );
      console.log({ daaaa: res.data });
      if (res.data) {
        setComments(res.data);
      } else {
        setComments([]);
      }
    } catch (err) {
      console.error(err);
    }
  }

  // todo fetch again
  useEffect(() => {
    fetchComments();
  }, []);

  let [newComment, setNewComment] = useState("");

  const onCommentType = (e) => {
    setNewComment(e.target.value);
  };
  const onAddComment = async () => {
    let commentData = {
      studentUsername: student.username,
      facultyUsername: token.preferred_username,
      comment: newComment,
    };

    console.log(commentData);
    try {
      let res = await axios.post(
        `${process.env.REACT_APP_BACKEND_BASE_URL}/studentcomment`,
        commentData,
        {
          headers: {
            // Authorization: `Bearer ${keycloak.token}`
          },
        }
      );

      if (res.data) {
        comments.push(res.data);
        setComments(comments);
      }
      console.log(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div>
      <Descriptions title="User Info">
        <Descriptions.Item label="UserName">
          {student.username}
        </Descriptions.Item>
        <Descriptions.Item label="Major">{student.major}</Descriptions.Item>
        <Descriptions.Item label="Gpa">{student.gpa}</Descriptions.Item>
        <Descriptions.Item label="CV">
          <a href={student.cv} target="_blank">
            {student.cv}
          </a>
        </Descriptions.Item>
        <Descriptions.Item label="City">{address.city}</Descriptions.Item>
        <Descriptions.Item label="State">{address.state}</Descriptions.Item>
        <Descriptions.Item label="Zip">{address.zip}</Descriptions.Item>
      </Descriptions>

      <Form name="basic">
        <Form.Item name="comment">
          <Input type="text" value={newComment} onChange={onCommentType} />
        </Form.Item>

        <Form.Item>
          <Button type="primary" htmlType="submit" onClick={onAddComment}>
            Add
          </Button>
        </Form.Item>
      </Form>
      <List
        style={{
          height: 150,
          overflow: "auto",
        }}
        size="small"
        header={<div>Comments</div>}
        bordered
        dataSource={comments}
        renderItem={(item) => (
          <div>
            <List.Item>
              <List.Item.Meta
                avatar={<Avatar src="" />}
                title={item.comment}
                description={item.facultyUsername}
              />
            </List.Item>
          </div>
        )}
      />

      {/* State: <span>{address ? address.state : ""}</span>
      City: <span>{address ? address.city : ""}</span>
      Zip: <span>{address ? address.zip : ""}</span> */}
    </div>
  );
};

export default ModalViewStudent;
