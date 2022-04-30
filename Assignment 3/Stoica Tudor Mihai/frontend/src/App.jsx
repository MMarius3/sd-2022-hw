import logo from "./logo.svg";
import "./App.css";
import PatientsList from "./components/patientsList";

import Modal from "react-modal";
import tr, { Component } from "react";
// import Consultation from "./components/consultation";

import React from "react";

import Consultation from "./components/consultation";

function App() {
  return (
    <PatientsList/>
    // <Consultation/>
  );     
}

export default App;
