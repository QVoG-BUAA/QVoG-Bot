import { Route, Routes } from 'react-router-dom'
import '~/assets/css/App.css';
import HomePage from '~/pages/HomePage/HomePage';
import { ResultPage } from './pages/ResultPage/ResultPage';
import Page404 from './pages/NotFoundPage/Page404';

function App() {
    return (
        <Routes>
            <Route exact path="/" element={<HomePage></HomePage>}></Route>
            <Route exact path="/results/:id" element={<ResultPage></ResultPage>}></Route>
            <Route exact path="/*" element={<Page404></Page404>}></Route>
        </Routes>
    );
}

export default App;