import { PropagateLoader } from 'react-spinners'
function LoadingImage() {
    return (
        <div>
            <PropagateLoader
                color="#36abd6"
                size={16}
            />
        </div>
    );
}

export default LoadingImage;