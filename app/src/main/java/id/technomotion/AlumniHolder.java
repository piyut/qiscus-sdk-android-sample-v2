package id.technomotion;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.QiscusChatRoom;

/**
 * Created by omayib on 18/09/17.
 */

public class AlumniHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final String TAG = "AlumniHolder";
    private TextView itemName;
    private TextView itemJob;
    private Alumni selectedAlumni;

    public AlumniHolder(View itemView) {
        super(itemView);
        itemName = (TextView) itemView.findViewById(R.id.textViewName);
        itemJob = (TextView) itemView.findViewById(R.id.textViewJob);
        itemView.setOnClickListener(this);
    }

    public void bindAlumni(Alumni alumni){
        this.selectedAlumni = alumni;
        this.itemName.setText(alumni.getName());
        this.itemJob.setText(alumni.getJob());
    }

    @Override
    public void onClick(View v) {
        Qiscus.buildChatRoomWith("siapa@email.com")
                .build(new Qiscus.ChatBuilderListener() {
                    @Override
                    public void onSuccess(QiscusChatRoom qiscusChatRoom) {
                        Log.d(TAG, "onSuccess() called with: qiscusChatRoom = [" + qiscusChatRoom + "]");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.d(TAG, "onError() called with: throwable = [" + throwable + "]");
                    }
                });
    }
}
