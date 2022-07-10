export default [
  {
    name: "Home",
    url: "/",
    icon: "faHome",
    roles: ["admin", "student", "faculty"],
  },
  {
    name: "Profile",
    url: "/profile",
    icon: "faUser",
    roles: ["student", "faculty"],
  },
  {
    name: "Advertisements",
    url: "/ads",
    icon: "faBriefcase",
    roles: ["student", "faculty"],
  },
  {
    name: "Students",
    url: "/students",
    icon: "faUser",
    roles: ["faculty"],
  },
  {
    name: "Employees",
    url: "/employees",
    icon: "faUser",
    roles: ["admin"],
  },
  {
    name: "Chat",
    url: "/chat",
    icon: "faPaperPlane",
    roles: ["student", "faculty"],
  },
];
