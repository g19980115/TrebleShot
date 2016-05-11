package com.genonbeta.TrebleShot.activity;

import android.content.*;
import android.net.*;
import android.os.*;
import android.widget.*;
import com.genonbeta.TrebleShot.*;
import com.genonbeta.TrebleShot.app.*;
import com.genonbeta.TrebleShot.helper.*;
import java.io.*;
import java.util.*;

public class ShareActivity extends GActivity
{
	public static final String ACTION_SEND = "genonbeta.intent.action.TREBLESHOT_SEND";
	public static final String ACTION_SEND_MULTIPLE = "genonbeta.intent.action.TREBLESHOT_SEND_MULTIPLE";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		boolean shouldContinue = false;
		String info = getString(R.string.something_went_wrong);

		if (getIntent() != null && getIntent().hasExtra(Intent.EXTRA_STREAM))
		{
			if (Intent.ACTION_SEND.equals(getIntent().getAction()) || ShareActivity.ACTION_SEND.equals(getIntent().getAction()))
			{
				Uri fileUri = getIntent().getParcelableExtra(Intent.EXTRA_STREAM);
				File file = ApplicationHelper.getFileFromUri(this, fileUri);

				if (file != null)
				{
					info = file.getName();

					shouldContinue = true;
				}
			}
			else if (Intent.ACTION_SEND_MULTIPLE.equals(getIntent().getAction()) || ShareActivity.ACTION_SEND_MULTIPLE.equals(getIntent().getAction()))
			{
				ArrayList<Uri> fileUris = getIntent().getParcelableArrayListExtra(Intent.EXTRA_STREAM);
				info = getString(R.string.item_selected, fileUris.size());

				shouldContinue = true;
			}
		}

		if (!shouldContinue)
		{
			Toast.makeText(this, R.string.file_type_not_supported_msg, Toast.LENGTH_SHORT).show();
			finish();
		}
		else
		{
			setContentView(R.layout.activity_share);

			TextView infoText = (TextView) findViewById(R.id.text);
			infoText.setText(info);
		}
	}
}