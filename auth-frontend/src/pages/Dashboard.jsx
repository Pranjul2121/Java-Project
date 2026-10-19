
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

export default function Dashboard() {
  const [data, setData] = useState(null);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    api.get("/dashboard")
      .then((response) => setData(response.data))
      .catch(() => {
        localStorage.removeItem("token");
        localStorage.removeItem("user");
        setError("Your session has expired. Please sign in again.");
      });
  }, []);

  function logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    navigate("/");
  }

  const user = JSON.parse(localStorage.getItem("user") || "{}");

  return (
    <div className="dashboard-container">
      <header className="dashboard-header">
        <h2>My Dashboard</h2>
        <button className="logout-button" onClick={logout}>
          Logout
        </button>
      </header>

      <section className="welcome-card">
        <h1>Hello, {user.name || "User"}!</h1>
        {error ? (
          <p>{error}</p>
        ) : data ? (
          <>
            <p>{data.message}</p>
            <p>Email: {data.email}</p>
          </>
        ) : (
          <p>Loading dashboard...</p>
        )}
      </section>
    </div>
  );
}
