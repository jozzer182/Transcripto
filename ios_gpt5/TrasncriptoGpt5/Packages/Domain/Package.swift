// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "Domain",
    platforms: [
        .iOS(.v16)
    ],
    products: [
        .library(name: "Domain", targets: ["Domain"])
    ],
    dependencies: [
        .package(name: "CoreCommon", path: "../CoreCommon")
    ],
    targets: [
        .target(name: "Domain", dependencies: ["CoreCommon"]) // local dep via relative path
    ]
)
