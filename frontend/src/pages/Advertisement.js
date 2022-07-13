import React, { useState, useEffect } from "react";
import { useKeycloak } from "@react-keycloak/web";
//import AuthrizedElement from "../components/AuthrizedElement";
import { Link } from 'react-router-dom';
import ReactECharts from "echarts-for-react";
// import jsonwebtoken from "jsonwebtoken";
import axios from "axios";

import { Form, FormGroup, Label, Input } from "reactstrap";
import "antd/dist/antd.css";
import { Button, Space, Table } from "antd";
import AddJob from "../components/AddJob";
import EditJob from "../components/EditJob";


const data = [
  { id: 1, description: 'George', benifit: 'Monkey', creator: 'qq', tags: [{ name: 'hello1' }, { name: 'hello2' }, { name: 'hello3' }], fileName: [{ name: '2211' }, { name: '2211' }] },
  { id: 2, description: 'Jeffrey', benifit: 'Giraffe', creator: 'ww', tags: [{ name: 'test1' }, { name: 'test2' }, { name: 'test3' }], fileName: [{ name: '3333' }, { name: '3333' }] },
  { id: 3, description: 'Alice', benifit: 'Giraffe', creator: 'ee', tags: [{ name: 'new1' }, { name: 'new2' }, { name: 'new3' }], fileName: [{ name: '1221' }, { name: '1221' }] },
  { id: 4, description: 'Foster', benifit: 'Tiger', creator: 'rr', tags: [{ name: 'jessica' }, { name: 'jessica2' }, { name: 'jessica3' }], fileName: [{ name: '3443' }, { name: '3443' }] },
  { id: 5, description: 'Tracy', benifit: 'Bear', creator: 'tt', tags: [{ name: 'hely1' }, { name: 'hely2' }, { name: 'hely3' }], fileName: [{ name: '5554' }, { name: '5554' }] }
];

const Advertisement = () => {
  const [filteredInfo, setFilteredInfo] = useState({});
  const [sortedInfo, setSortedInfo] = useState({});

  const handleChange = (pagination, filters, sorter) => {
    console.log('Various parameters', pagination, filters, sorter);
    setFilteredInfo(filters);
    setSortedInfo(sorter);
  };



  const columns = [
    {
      title: "Id:",
      dataIndex: 'id',
      key: 'id',
      filteredValue: filteredInfo.id || null,
      onFilter: (value, record) => record.id.includes(value),
      sorter: (a, b) => a.id.length - b.id.length,
      sortOrder: sortedInfo.columnKey === 'id' ? sortedInfo.order : null,
      ellipsis: true,
    },
    {
      title: "Description:",
      dataIndex: 'description',
      key: 'description',
      filters: [
        {
          text: 'George',
          value: 'George',
        },
        {
          text: 'Alice',
          value: 'Alice',
        },
      ],
      filteredValue: filteredInfo.description || null,
      onFilter: (value, record) => record.description.includes(value),
      sorter: (a, b) => a.description.length - b.description.length,
      sortOrder: sortedInfo.columnKey === 'description' ? sortedInfo.order : null,
      ellipsis: true,
    },
    {
      title: "Benifit:",
      dataIndex: 'benifit',
      key: 'benifit',
      sorter: (a, b) => a.benifit - b.benifit,
      sortOrder: sortedInfo.columnKey === 'benifit' ? sortedInfo.order : null,
      ellipsis: true,
    },
    {
      title: "Tags:",
      dataIndex: 'tags',
      key: 'tags',
      filters: [
        {
          text: 'loyo',
          value: 'loyo',
        },
        {
          text: 'deol',
          value: 'deol',
        },
      ],
      render: (tags) => { return (<div>{tags.map((ind) => (<p><Link to="/">{ind.name}</Link></p>))}</div>) },
      filteredValue: filteredInfo.tags || null,
      onFilter: (value, record) => record.tags.includes(value),
      sorter: (a, b) => ((a.tags.map((ind) => ind.length)) - (b.tags.map((ind) => ind.length))),
      sortOrder: sortedInfo.columnKey === 'tags' ? sortedInfo.order : null,
      ellipsis: true,
    },
    {
      title: "Creator:",
      dataIndex: 'creator',
      key: 'creator',
      filters: [
        {
          text: 'yy',
          value: 'yy',
        },
        {
          text: 'ee',
          value: 'ee',
        },
      ],
      filteredValue: filteredInfo.creator || null,
      onFilter: (value, record) => record.creator.includes(value),
      sorter: (a, b) => a.creator.length - b.creator.length,
      sortOrder: sortedInfo.columnKey === 'creator' ? sortedInfo.order : null,
      ellipsis: true,
    },
    {
      title: "File:",
      dataIndex: 'fileName',
      key: 'fileName',
      filters: [
        {
          text: '5656',
          value: '5656',
        },
        {
          text: '7666',
          value: '7666',
        },
      ],
      render: (fileName) => { return (<div>{fileName.map((ind) => (<p><Link to="/">{ind.name}</Link></p>))}</div>) },
      filteredValue: filteredInfo.fileName || null,
      onFilter: (value, record) => record.fileName.includes(value),
      sorter: (a, b) => ((a.fileName.map((ind) => ind.length)) - (b.fileName.map((ind) => ind.length))),
      sortOrder: sortedInfo.columnKey === 'fileName' ? sortedInfo.order : null,
      ellipsis: true,
    },
    {
      title: "Action:",
      render: (id) => { return (
        // <Button className="btn btn-primary add-btn">
        //     Edit
        // </Button>
        <EditJob props={id}/>
      ) },
    }
  ];

  const columns1 = [
    {
      title: "Id:",
      dataIndex: 'id',
      key: 'id',
      filteredValue: filteredInfo.id || null,
      onFilter: (value, record) => record.id.includes(value),
      sorter: (a, b) => a.id.length - b.id.length,
      sortOrder: sortedInfo.columnKey === 'id' ? sortedInfo.order : null,
      ellipsis: true,
    },
    {
      title: "Description:",
      dataIndex: 'description',
      key: 'description',
      filters: [
        {
          text: 'George',
          value: 'George',
        },
        {
          text: 'Alice',
          value: 'Alice',
        },
      ],
      filteredValue: filteredInfo.description || null,
      onFilter: (value, record) => record.description.includes(value),
      sorter: (a, b) => a.description.length - b.description.length,
      sortOrder: sortedInfo.columnKey === 'description' ? sortedInfo.order : null,
      ellipsis: true,
    },
    {
      title: "Benifit:",
      dataIndex: 'benifit',
      key: 'benifit',
      sorter: (a, b) => a.benifit - b.benifit,
      sortOrder: sortedInfo.columnKey === 'benifit' ? sortedInfo.order : null,
      ellipsis: true,
    },
    {
      title: "Tags:",
      dataIndex: 'tags',
      key: 'tags',
      filters: [
        {
          text: 'loyo',
          value: 'loyo',
        },
        {
          text: 'deol',
          value: 'deol',
        },
      ],
      render: (tags) => { return (<div>{tags.map((ind) => (<p><Link to="/">{ind.name}</Link></p>))}</div>) },
      filteredValue: filteredInfo.tags || null,
      onFilter: (value, record) => record.tags.includes(value),
      sorter: (a, b) => ((a.tags.map((ind) => ind.length)) - (b.tags.map((ind) => ind.length))),
      sortOrder: sortedInfo.columnKey === 'tags' ? sortedInfo.order : null,
      ellipsis: true,
    },
    {
      title: "Creator:",
      dataIndex: 'creator',
      key: 'creator',
      filters: [
        {
          text: 'yy',
          value: 'yy',
        },
        {
          text: 'ee',
          value: 'ee',
        },
      ],
      filteredValue: filteredInfo.creator || null,
      onFilter: (value, record) => record.creator.includes(value),
      sorter: (a, b) => a.creator.length - b.creator.length,
      sortOrder: sortedInfo.columnKey === 'creator' ? sortedInfo.order : null,
      ellipsis: true,
    },
    {
      title: "File:",
      dataIndex: 'fileName',
      key: 'fileName',
      filters: [
        {
          text: '5656',
          value: '5656',
        },
        {
          text: '7666',
          value: '7666',
        },
      ],
      render: (fileName) => { return (<div>{fileName.map((ind) => (<p><Link to="/">{ind.name}</Link></p>))}</div>) },
      filteredValue: filteredInfo.fileName || null,
      onFilter: (value, record) => record.fileName.includes(value),
      sorter: (a, b) => ((a.fileName.map((ind) => ind.length)) - (b.fileName.map((ind) => ind.length))),
      sortOrder: sortedInfo.columnKey === 'fileName' ? sortedInfo.order : null,
      ellipsis: true,
    },
    {
      title: "Action:",
      render: (id) => { return (
        <Button className="btn btn-primary add-btn">
            Apply
        </Button>
      ) },
    }
  ];

  return (
    <div className="container">
      <div className='row'>
        <div className='col-md-12 text-right'>
          <AddJob/>
        </div>
        <div className='col-md-12'>
          <h5>User</h5>
          <Table columns={columns} dataSource={data} onChange={handleChange} />
        </div>
        <div className='col-md-12'>
          <h5>User</h5>
          <Table columns={columns1} dataSource={data} onChange={handleChange} />
        </div>
      </div>
    </div>
  );
};

export default Advertisement;
