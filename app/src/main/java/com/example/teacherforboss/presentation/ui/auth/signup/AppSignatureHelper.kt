import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.os.Build
import android.util.Base64
import android.util.Log
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Arrays

class AppSignatureHelper(context: Context) : ContextWrapper(context) {
    val TAG = AppSignatureHelper::class.java.simpleName
    private val HASH_TYPE = "SHA-256"
    val NUM_HASHED_BYTES = 9
    val NUM_BASE64_CHAR = 11

    init {
        getAppSignatures()
    }

    /**
     * Get all the app signatures for the current package
     * @return
     */
    fun getAppSignatures(): ArrayList<String>? {
        val appCodes = ArrayList<String>()
        try {
            // Get all package signatures for the current package
            val packageName = packageName
            val packageManager = packageManager
//            val signatures: Array<Signature> = packageManager.getPackageInfo(
//                packageName,
//                PackageManager.GET_SIGNATURES
//            ).signatures

            val signatures = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                // Android 9 (API level 28) 이상에서는 GET_SIGNING_CERTIFICATES를 사용
                val signingInfo = packageManager.getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNING_CERTIFICATES
                ).signingInfo
                signingInfo.apkContentsSigners
            } else {
                // Android 8.1 (API level 27) 이하에서는 GET_SIGNATURES를 사용
                val packageInfo = packageManager.getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNATURES
                )
                packageInfo.signatures
            }

            // For each signature create a compatible hash
            for (signature in signatures) {
                val hash = hash(packageName, signature.toCharsString())
                if (hash != null) {
                    appCodes.add(String.format("%s", hash))
                }
                Log.e("hash", "Hash $hash")
            }
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, "Unable to find package to obtain hash.", e)
        }
        return appCodes
    }

    private fun hash(packageName: String, signature: String): String? {
        val appInfo = "$packageName $signature"
        try {
            val messageDigest = MessageDigest.getInstance(HASH_TYPE)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                messageDigest.update(appInfo.toByteArray(StandardCharsets.UTF_8))
            }
            var hashSignature = messageDigest.digest()

            // truncated into NUM_HASHED_BYTES
            hashSignature = Arrays.copyOfRange(hashSignature, 0, NUM_HASHED_BYTES)
            // encode into Base64
            var base64Hash: String =
                Base64.encodeToString(hashSignature, Base64.NO_PADDING or Base64.NO_WRAP)
            base64Hash = base64Hash.substring(0, NUM_BASE64_CHAR)
            Log.e(TAG, String.format("pkg: %s -- hash: %s", packageName, base64Hash))
            return base64Hash
        } catch (e: NoSuchAlgorithmException) {
            Log.e(TAG, "hash:NoSuchAlgorithm", e)
        }
        return null
    }
}