package com.github.panchitoboy.shiro.jwt.verifier;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;

public class MACVerifierExtended extends MACVerifier {

    private final ReadOnlyJWTClaimsSet claimsSet;

    public MACVerifierExtended(final byte[] sharedSecret, ReadOnlyJWTClaimsSet claimsSet) {
        super(sharedSecret);
        this.claimsSet = claimsSet;
    }

    public MACVerifierExtended(final String sharedSecretString, ReadOnlyJWTClaimsSet claimsSet) {
        super(sharedSecretString);
        this.claimsSet = claimsSet;
    }

    @Override
    public boolean verify(final JWSHeader header, final byte[] signingInput, final Base64URL signature) throws JOSEException {
        boolean value = super.verify(header, signingInput, signature);
        long time = System.currentTimeMillis();

        return value && claimsSet.getNotBeforeTime().getTime() <= time && time < claimsSet.getExpirationTime().getTime();
    }
}
