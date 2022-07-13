import React, { useState, useEffect } from "react";
import { Link } from 'react-router-dom';
import ReactECharts from "echarts-for-react";
// import jsonwebtoken from "jsonwebtoken";
import axios from "axios";

// import { useKeycloak } from "@react-keycloak/web";
//import AuthrizedElement from "../components/AuthrizedElement";
import { useSelector } from "react-redux";
import { Form, FormGroup, Label, Input } from "reactstrap";
import "antd/dist/antd.css";
import { Button, Space, Table } from "antd";


const data = [
  { id: 1, description: 'George', benifit: 'Monkey', creator: 'qq', tags: [{name:'hello1'},{name:'hello2'},{name:'hello3'}], fileName: [{name:'2211'}, {name:'2211'}] },
  { id: 2, description: 'Jeffrey', benifit: 'Giraffe', creator: 'ww', tags: [{name:'test1'}, {name:'test2'}, {name:'test3'}], fileName: [{name:'3333'}, {name:'3333'}] },
  { id: 3, description: 'Alice', benifit: 'Giraffe', creator: 'ee', tags: [{name:'new1'}, {name:'new2'}, {name:'new3'}], fileName: [{name:'1221'}, {name:'1221'}] },
  { id: 4, description: 'Foster', benifit: 'Tiger', creator: 'rr', tags: [{name:'jessica'}, {name:'jessica2'}, {name:'jessica3'}], fileName: [{name:'3443'}, {name:'3443'}] },
  { id: 5, description: 'Tracy', benifit: 'Bear', creator: 'tt', tags: [{name:'hely1'}, {name:'hely2'}, {name:'hely3'}], fileName: [{name:'5554'}, {name:'5554'}] },
  { id: 6, description: 'Joesph', benifit: 'Lion', creator: 'yy', tags: [{name:'loyo'}], fileName: [{name:'5656'}] },
  { id: 7, description: 'Tania', benifit: 'Deer', creator: 'yy', tags: [{name:'deol'}], fileName: [{name:'7666'}] },
  { id: 8, description: 'Chelsea', benifit: 'Tiger', creator: 'uu', tags: [{name:'tede'}], fileName: [{name:'7878'}] },
  { id: 9, description: 'Benedict', benifit: 'Tiger', creator: 'ii', tags: [{name:'tepo'}], fileName: [{name:'8777'}] },
  { id: 10, description: 'Chadd', benifit: 'Lion', creator: 'oo', tags: [{name:'nono'}], fileName: [{name:'8899'}] },
  { id: 11, description: 'Delphine', benifit: 'Deer', creator: 'pp', tags: [{name:'momo'}], fileName: [{name:'7766'}] },
  { id: 12, description: 'Elinore', benifit: 'Bear', creator: 'll', tags: [{name:'lolo'}], fileName: [{name:'9090'}] },
  { id: 13, description: 'Stokes', benifit: 'Tiger', creator: 'kk', tags: [{name:'yoyo'}], fileName: [{name:'9999'}] },
  { id: 14, description: 'Tamara', benifit: 'Lion', creator: 'jj', tags: [{name:'gogo'}], fileName: [{name:'9898'}] },
  { id: 15, description: 'Zackery', benifit: 'Bear', creator: 'hh', tags: [{name:'dodo'}], fileName: [{name:'9666'}] }
];

const options = {
  xAxis: {
    type: 'category',
    data: ['job ad',
      'students per state',
      'students per city',
      'Tags',
      'Tags with location',
      'Average time',
      'Add at least 4'
    ],
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      data: [120, 200, 150, 80, 70, 110, 130],
      type: 'bar'
    }
  ]
};

const HomePage = ({ keycloak }) => {
  // let token = jsonwebtoken.decode(keycloak.token);
  // const [keycloak, initialized] = useKeycloak();
  
  async function getJobAd() {
    let res = await axios.get(`${process.env.REACT_APP_BACKEND_BASE_URL}/jobAd`,
    // let res = await axios.get(`http://localhost:8080/jobAd`,
      {
        headers: {
          //todo auth
        },
      }
    );
    console.log(res.data);
    // if (res.data) {
    //   // console.log({ address: JSON.parse(res.data.address) });
    //   // setExtraDetails(res.data);
    //   // setAddressDetails(JSON.parse(res.data.address));
    //   // setSelectedFile({ url: res.data.cv });
    //   // setSelectedFile(res.data.cv);
    // }
  }

  useEffect(() => {
    getJobAd();
  }, []);

  const [filteredInfo, setFilteredInfo] = useState({});
  const [sortedInfo, setSortedInfo] = useState({});

  const handleChange = (pagination, filters, sorter) => {
    console.log('Various parameters', pagination, filters, sorter);
    setFilteredInfo(filters);
    setSortedInfo(sorter);
  };

  const clearFilters = () => {
    setFilteredInfo({});
  };

  const clearAll = () => {
    setFilteredInfo({});
    setSortedInfo({});
  };

  const setAgeSort = () => {
    setSortedInfo({
      order: 'descend',
      columnKey: 'age',
    });
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
      render: (tags) => { return (<div>{tags.map((ind)=>(<p><Link to="/">{ind.name}</Link></p>))}</div>)},
      filteredValue: filteredInfo.tags|| null,
      onFilter: (value, record) => record.tags.includes(value),
      sorter: (a, b) => ((a.tags.map((ind)=>ind.length))-(b.tags.map((ind)=>ind.length))),
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

      render: (fileName) => { return (<div>{fileName.map((ind)=>(<p><Link to="/">{ind.name}</Link></p>))}</div>)},
      filteredValue: filteredInfo.fileName|| null,
      onFilter: (value, record) => record.fileName.includes(value),
      sorter: (a, b) => ((a.fileName.map((ind)=>ind.length))-(b.fileName.map((ind)=>ind.length))),
      sortOrder: sortedInfo.columnKey === 'fileName' ? sortedInfo.order : null,
      ellipsis: true,
    },
  ];

  return (
    <div>
      <h1>Home Page</h1>
      {/* <strong>Anyone can access this page</strong> */}
      {/* <Input
        id="exampleText"
        name="text"
        type="textarea"
        defaultValue={keycloak.token}
      />
      {/* <textarea width="150">{keycloak.token}</textarea> */}
      {/* {initialized ? (
        keycloak.authenticated && (
          <pre>{JSON.stringify(keycloak, undefined, 2)}</pre>
        )
      ) : (
        <h2>keycloak initializing ....!!!!</h2>
      )} */}



      {/* Table */}
      <ReactECharts option={options} className='top' />

      {/* <Space style={{marginBottom: 16, marginTop: 30}}>
          <Button onClick={setAgeSort}>Sort age</Button>
          <Button onClick={clearFilters}>Clear filters</Button>
          <Button onClick={clearAll}>Clear filters and sorters</Button>
        </Space> */}
      <div className='row'>
        <div className='col-md-6'>
          <h5>Display the last 10 job advertisements</h5>
          <Table columns={columns} dataSource={data} onChange={handleChange} />
        </div>
        <div className='col-md-6'>
          <h5>Display 10 most recently applied job advertisements</h5>
          <Table columns={columns} dataSource={data} onChange={handleChange} />
        </div>
      </div>
      {/* End Table */}

    </div>
  );
};
export default HomePage;
