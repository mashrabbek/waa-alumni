import { Space, Table, Tag } from "antd";
import React from "react";
const columns = [
  {
    title: "Company name",
    dataIndex: "companyName",
    key: "companyName",
  },
  {
    title: "Start date",
    dataIndex: "startDate",
    key: "startDate",
  },
  {
    title: "End date",
    dataIndex: "endDate",
    key: "endDate",
  },
  {
    title: "Reason to leave",
    dataIndex: "reasonToLeave",
    key: "reasonToLeave",
  },
  {
    title: "Tags",
    key: "tags",
    dataIndex: "tags",
    render: (_, { tags }) => (
      <>
        {tags.map((tag) => {
          let color = tag.length > 5 ? "geekblue" : "green";

          if (tag === "loser") {
            color = "volcano";
          }

          return (
            <Tag color={color} key={tag}>
              {tag.toUpperCase()}
            </Tag>
          );
        })}
      </>
    ),
  },
  {
    title: "Action",
    key: "action",
    render: (_, record) => (
      <Space size="middle">
        <a>Delete</a>
      </Space>
    ),
  },
];
const data = [
  {
    companyName: "John Brown",
    startDate: 32,
    endDate: 32,
    reasonToLeave: "New York No. 1 Lake Park",
    tags: ["nice", "developer"],
  },
  {
    companyName: "John Brown",
    startDate: 32,
    endDate: 32,
    reasonToLeave: "London No. 1 Lake Park",
    tags: ["loser"],
  },
  {
    companyName: "John Brown",
    startDate: 32,
    endDate: 32,
    reasonToLeave: "Sidney No. 1 Lake Park",
    tags: ["cool", "teacher"],
  },
];

const JobHistory = () => <Table columns={columns} dataSource={data} />;

export default JobHistory;
