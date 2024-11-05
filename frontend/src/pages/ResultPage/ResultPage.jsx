import InflateBox from '~/components/InflateBox';
import { Helmet } from 'react-helmet';
import { useParams, useNavigate } from 'react-router-dom';

import LogoNav from '~/parts/LogoNav/LogoNav';
import LanguageNav from '~/parts/LanguageNav/LanguageNav';
import { Footer } from '~/parts/Footer/Footer';
import { useEffect, useState } from 'react';
import { Results } from '~/parts/Results/Results';

import './ResultPage.css';
import { LoadCircle } from '~/components/LoadCircle/LoadCircle';
import api from '~/services/api';
import stall from '~/services/stall';

export function ResultPage() {
    const navigate = useNavigate();

    const params = useParams();

    const [online, setOnline] = useState(false);
    const [data, setData] = useState();
    const [info, setInfo] = useState();

    useEffect(() => {
        if (data !== undefined) {
            const cloneUrl = data.request.cloneUrl;
            const htmlUrl = cloneUrl.substring(0, cloneUrl.length - 4);
            setInfo({
                repository: data.request.repoName,
                repositoryUrl: htmlUrl,
                branch: data.request.branch,
                branchUrl: htmlUrl + '/tree/' + data.request.branch,
                commit: data.request.commitId.substring(0, 10),
                commitUrl: htmlUrl + '/commits/' + data.request.commitId,
            });
        }
    }, [data]);

    const [id] = useState(params.id);

    const [error, setError] = useState();

    useEffect(() => {
        if (id !== undefined) {
            populateResult(id);
        }
    }, [id]);

    useEffect(() => {
        if (online && data === undefined && id !== undefined) {
            populateResult(id);
        }
    }, [id]);

    return (
        <div className='ResultPage'>
            <Helmet>
                <title>Scan Result</title>
            </Helmet>
            <InflateBox overflow={true}>
                <LogoNav data={info} online={online} />
                <hr />
                {data === undefined
                    ? (
                        error === undefined
                            ? <LoadCircle sx={{ height: "100px", marginTop: "10%" }} />
                            : renderError(error)
                    )
                    : (
                        <div>
                            <LanguageNav language={data.result.language} id={data.request.id} />
                            <div className='ResultPage__content'>
                                {renderOverview(data.report.queryCount, data.report.bugCount, data.report.totalMilliseconds / 1000)}
                            </div>
                            <Results results={data.report.results}></Results>
                        </div>
                    )
                }
                <Footer setOnline={setOnline} />
            </InflateBox>
        </div>
    )

    function formatSecondDuration(duration) {
        if (duration < 60) {
            return <span><b>{duration.toFixed(3)}</b>s</span>;
        } else {
            const m = Math.floor(duration / 60);
            const s = Math.floor(duration - m * 60);
            return <span><b>{m}</b>m<b>{s}</b>s</span>;
        }
    }

    function renderOverview(queryCount, bugCount, totalMilliseconds) {
        return (
            <div className='ResultPage__overview'>
                <h2>Overview</h2>
                {bugCount === 0
                    ? <p><b style={{ color: "greenyellow" }}>Congratulations!</b> No potential bugs found in your repository.</p>
                    : <p>We have ran <b>{queryCount}</b> queries on your repository in {formatSecondDuration(totalMilliseconds)}, and found <b style={{ color: "red" }}>{bugCount}</b> potential bugs.</p>
                }
            </div>
        )
    }

    function renderError(message) {
        return (
            <div className='ResultPage__error'>
                {online ? message[0] ?
                    <div>
                        <h2>One or more errors occurred when we scan your repository!</h2>
                        <p>{message}</p>
                    </div>
                    : <h2>{message}</h2>
                    : <h2>Server offline, report not available.</h2>
                }
            </div>
        )
    }

    async function populateResult(id) {
        const dto = await stall(api.get('/api/result/' + id), 2000);
        if (dto.status === 200) {
            setData(dto.data);
        } else if (dto.status === 404) {
            navigate('/404');
        } else {
            setError([true, dto.message]);
        }
    }
}