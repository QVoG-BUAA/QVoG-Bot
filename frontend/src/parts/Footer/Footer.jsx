import { useEffect, useState } from 'react';

import api from '~/services/api';
import stall from '~/services/stall';

import './Footer.css';

export function Footer({ setOnline = null }) {
    const [error, setError] = useState();
    const [serverStatus, setServerStatus] = useState();

    useEffect(() => {
        fetchServerStatus();
        setInterval(fetchServerStatus, 10000);
    }, []);

    useEffect(() => {
        if (setOnline !== null) {
            setOnline(error === undefined ? false : !error);
        }
    }), [setOnline, error];

    const getProfile = () => error === undefined ?
        <span className='Footer__status Footer__status_load'><i className='dot'></i>Loading...</span> :
        error ? <span className='Footer__status Footer__status_error'><i className='dot'></i>Offline</span> :
            <span className='Footer__status Footer__status_ok'><i className='dot'></i>Online</span>

    useEffect(() => {
        setServerStatus(getProfile());
    }, [error]);

    return (
        <div className='Footer'>
            <p>Server status: {serverStatus}</p>
            <hr className='separator' />
            <div>
                <p>Homepage: <a href="https://www.gitlink.org.cn/softbot/10033" target='_blank'>GitLink</a></p>
            </div>
        </div>
    );

    async function fetchServerStatus() {
        const dto = await stall(api.get('/api/ping'));
        setError(dto.status != 200);
    }
}