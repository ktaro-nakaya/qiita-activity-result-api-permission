package jp.co.casareal.activityresultapiforpermission

import android.Manifest
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // 単一パーミッションの場合ActivityResultContractにRequestPermissionを指定
    private val launcherSingle =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                Toast.makeText(this, "単一Permissionが取得できた", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, "取得できなかった単一Permissionがある", Toast.LENGTH_SHORT).show()
            }
        }

    // 複数パーミッションの場合ActivityResultContractにRequestMultiplePermissionsを指定
    private val launcherMulti =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { resultMap ->
            resultMap.values.forEach { boolResult ->
                if (!boolResult) {
                    Toast.makeText(this, "取得できなかったPermissionがある", Toast.LENGTH_SHORT).show()
                    return@registerForActivityResult
                }
            }
            Toast.makeText(this, "すべてのPermissionが取得できた", Toast.LENGTH_SHORT).show()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // 取得したいパーミッションをlaunchの引数に渡す
        findViewById<Button>(R.id.btnSinglePermission).setOnClickListener {
            launcherSingle.launch(Manifest.permission.READ_CALENDAR)
        }

        // 取得したいパーミッションも文字列配列をlaunchの引数に渡す
        findViewById<Button>(R.id.btnMultiPermission).setOnClickListener {
            launcherMulti.launch(
                arrayOf(
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.CAMERA
                )
            )
        }
    }
}